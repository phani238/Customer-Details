import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';
import { withInterceptors } from '@angular/common/http';
import { loggerInterceptor } from './services/logger.interceptor';
import { provideRouter } from '@angular/router';
import { MatDialogModule, MatDialogRef, MatDialogConfig } from '@angular/material/dialog';
import { ToastrModule } from 'ngx-toastr';
import { provideToastr } from 'ngx-toastr';
import { routes } from './app.routes';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';
import { CommonModule } from '@angular/common';

imports: [
  CommonModule,
  MatDialogModule,
  MatDialogRef,
  MatDialogConfig,
  FormsModule,
  ReactiveFormsModule,
  DatePipe,
  NgxPaginationModule,
  ToastrModule.forRoot({
    timeOut: 100000, // 10 seconds
    closeButton: true,
    progressBar: true,
  }),
]


export const appConfig: ApplicationConfig = {
  providers: [CommonModule, provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes), provideHttpClient(withInterceptors([loggerInterceptor])), DatePipe, provideToastr()]
};
