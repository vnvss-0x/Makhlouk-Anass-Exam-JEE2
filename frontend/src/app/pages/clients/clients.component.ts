import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { ClientService } from '../../services/client.service';
import { Client } from '../../models/models';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-clients',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './clients.component.html',
})
export class ClientsComponent implements OnInit {
  clients: Client[] = [];
  isLoading = true;

  constructor(
    private clientService: ClientService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(): void {
    this.isLoading = true;
    this.clientService.getAll().subscribe({
      next: (data: Client[]) => { this.clients = data; this.isLoading = false; },
      error: () => { this.isLoading = false; }
    });
  }

  deleteClient(id: number): void {
    if (confirm('Confirmer la suppression de ce client ?')) {
      this.clientService.delete(id).subscribe({
        next: () => this.loadClients()
      });
    }
  }
}
