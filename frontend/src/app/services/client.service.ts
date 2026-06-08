import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../models/models';

@Injectable({ providedIn: 'root' })
export class ClientService {

  private readonly API = 'http://localhost:8080/api/clients';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Client[]> {
    return this.http.get<Client[]>(this.API);
  }

  getById(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.API}/${id}`);
  }

  create(client: Client): Observable<Client> {
    return this.http.post<Client>(this.API, client);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${id}`);
  }
}
