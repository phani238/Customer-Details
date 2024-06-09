package com.maybank.customerDetails.config;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.maybank.customerDetails.entity.Customer;
import com.maybank.customerDetails.repository.CustomerDetailsRepository;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class BatchConfig {

	@Autowired
	CustomerDetailsRepository repo;

	public FlatFileItemReader<Customer> itemReader() {
		FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("src/main/resources/static/dataSource.txt"));
		itemReader.setName("txt-reader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}

	private LineMapper<Customer> lineMapper() {
		DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter("|");
		tokenizer.setNames("ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME", "CUSTOMER_ID");
		tokenizer.setStrict(false);

		// Date parsing logic has been added
		HashMap<Class, PropertyEditor> customEditors = new HashMap<>();
		customEditors.put(Time.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(Time.valueOf(text));
			}
		});
		customEditors.put(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = (Date) dateFormat.parse(text);
					setValue(date);
				} catch (ParseException e) {
					throw new IllegalArgumentException("Invalid date format: " + text, e);
				}
			}
		});

		BeanWrapperFieldSetMapper mapper = new BeanWrapperFieldSetMapper<>();
		mapper.setTargetType(Customer.class);
		mapper.setCustomEditors(customEditors);

		lineMapper.setFieldSetMapper(mapper);
		lineMapper.setLineTokenizer(tokenizer);

		return lineMapper;
	}

	@Bean
	public CustomerProcessor processor() {
		return new CustomerProcessor();
	}

	@Bean
	public RepositoryItemWriter<Customer> itemWriter() {
		RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
		writer.setRepository(repo);
		writer.setMethodName("save");
		return writer;
	}

	@Bean
	public Step step(JobRepository repository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("txt-step", repository).<Customer, Customer>chunk(10, transactionManager)
				.reader(itemReader()).processor(processor()).writer(itemWriter()).taskExecutor(taskExecutor()).build();
	}

	private TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
		asyncTaskExecutor.setConcurrencyLimit(10);
		return asyncTaskExecutor;
	}

	@Bean
	public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("txt-job", jobRepository).flow(step(jobRepository, transactionManager)).end().build();
	}
}
