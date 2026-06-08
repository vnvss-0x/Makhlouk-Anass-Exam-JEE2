import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Credit, StatutCredit } from '../models/models';

@Injectable({ providedIn: 'root' })
export class CreditService {

  private readonly API = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Credit[]> {
    return this.http.get<Credit[]>(`${this.API}/credits`);
  }

  getById(id: number): Observable<Credit> {
    return this.http.get<Credit>(`${this.API}/credits/${id}`);
  }

  getByClient(clientId: number): Observable<Credit[]> {
    return this.http.get<Credit[]>(`${this.API}/clients/${clientId}/credits`);
  }

  getByStatut(statut: StatutCredit): Observable<Credit[]> {
    return this.http.get<Credit[]>(`${this.API}/credits/statut/${statut}`);
  }

  create(credit: Credit): Observable<Credit> {
    return this.http.post<Credit>(`${this.API}/credits`, credit);
  }

  updateStatut(id: number, statut: StatutCredit): Observable<Credit> {
    const params = new HttpParams().set('statut', statut);
    return this.http.put<Credit>(`${this.API}/credits/${id}/statut`, null, { params });
  }
}
