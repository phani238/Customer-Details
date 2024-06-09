import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { EditCustomerComponent } from '../EditCustomer/edit-customer/edit-customer.component';
@Component({
  selector: 'app-customer-search',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './customer-search.component.html',
  styleUrl: './customer-search.component.css'
})
export class CustomerSearchComponent {
  showResult: boolean = false;
  custId: string = '';
  accountNumber: string = '';
  desc: string = '';
  empVal: string = '';
  http = inject(HttpClient);
  serverURL: string = 'http://localhost:9090/api/customer';
  getUrl: string = '';
  public getAllCustomer: any;

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
    this.showResult = false;
  }

  OnCustIdChange(event: Event) {
    this.custId = (event.target as HTMLInputElement).value;
  }

  OnAcctNoChange(event: Event) {
    this.accountNumber = (event.target as HTMLInputElement).value;
  }

  OnDescChange(event: Event) {
    this.desc = (event.target as HTMLInputElement).value;
  }

  searchEntries() {
    const headers = new HttpHeaders().set('api-key', 'maybank');
    let params = new HttpParams().append('accountNo', this.accountNumber).append('custId', this.custId).append('desc', this.desc);
    this.http.get(this.serverURL, { headers, params }).subscribe((res) => {
      this.showResult = true;
      this.getAllCustomer = res;
    });
  }

  editEntries(e: any) {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.height = '800px';
    dialogConfig.width = '900px';
    dialogConfig.position = {
      top: '-40hw',
      left: '30vw'
    };

    dialogConfig.data = {
      title: "Update Customer Details", accountNumber: e.accountNumber, trxAmount: e.trxAmount, description: e.description, trxDate: e.trxDate, trxTime: e.trxTime, customerId: e.customerId, id: e.id
    }
    const dialogRef = this.dialog.open(EditCustomerComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(result => {
      this.searchEntries();
    });
  }


  reset() {
    this.accountNumber = this.empVal;
    this.custId = this.empVal;
    this.desc = this.empVal;
  }
}
