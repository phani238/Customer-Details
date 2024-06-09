import { Component, inject, Inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import {
  MAT_DIALOG_DATA,
  MatDialogRef,
  MatDialogTitle,
  MatDialogContent,
  MatDialogActions,
  MatDialogClose,
} from '@angular/material/dialog';

@Component({
  selector: 'app-edit-customer',
  standalone: true,
  imports: [MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, FormsModule, CommonModule, DatePipe],
  templateUrl: './edit-customer.component.html',
  styleUrl: './edit-customer.component.css'
})
export class EditCustomerComponent {
  http = inject(HttpClient);
  title: string = '';
  id: number = 0;
  accountNumber: string = '';
  trxAmount: number = 0;
  description: string = '';
  trxDate: string = '';
  trxTime: string = '';
  customerId: string = '';
  updateCustomerUrl: string = 'http://localhost:9090/api/customer';
  public updateCustomer: any;

  constructor(public dialogRef: MatDialogRef<EditCustomerComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData, private datePipe: DatePipe, private toastrService: ToastrService) {
  }

  ngOnInit() {
    this.title = this.data.title;
    this.accountNumber = this.data.accountNumber;
    this.trxAmount = this.data.trxAmount;
    this.description = this.data.description;
    this.trxDate = this.data.trxDate;
    this.trxTime = this.data.trxTime;
    this.customerId = this.data.customerId;
    this.id = this.data.id;
  }

  get formattedDate() {
    return this.datePipe.transform(this.trxDate, 'yyyy-MM-dd');
  }

  onClose(): void {
    this.dialogRef.close();
  }

  onUpdate(): void {
    this.updateCustomer = {
      id: this.id,
      accountNumber: this.accountNumber,
      trxAmount: this.trxAmount,
      description: this.description,
      trxDate: this.trxDate,
      trxTime: this.trxTime,
      customerId: this.customerId
    }
    console.log(this.updateCustomer);
    const headers = new HttpHeaders().set('api-key', 'maybank');
    this.http.put(this.updateCustomerUrl, this.updateCustomer, { headers }).subscribe((res) => {
      if (res) {
        this.toastrService.success('Updated Successfully!', 'Update Success!');
        this.onClose();
      }
    })
  }
}

export interface DialogData {
  title: string;
  accountNumber: string;
  trxAmount: number;
  description: string;
  trxDate: string;
  trxTime: string;
  customerId: string;
  id: number;
}
