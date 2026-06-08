import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { CreditService } from '../../services/credit.service';
import { Credit } from '../../models/models';

@Component({
  selector: 'app-mes-credits',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './mes-credits.component.html',
})
export class MesCreditsComponent implements OnInit {
  credits: Credit[] = [];
  isLoading = true;

  constructor(private creditService: CreditService) {}

  ngOnInit(): void {
    this.creditService.getAll().subscribe({
      next: (data: Credit[]) => { this.credits = data; this.isLoading = false; },
      error: () => { this.isLoading = false; }
    });
  }

  getStatutClass(statut: string | undefined): string {
    switch (statut) {
      case 'ACCEPTE': return 'bg-green-100 text-green-700';
      case 'REJETE': return 'bg-red-100 text-red-700';
      default: return 'bg-yellow-100 text-yellow-700';
    }
  }

  getStatutLabel(statut: string | undefined): string {
    switch (statut) {
      case 'ACCEPTE': return '✓ Accepté';
      case 'REJETE': return '✗ Rejeté';
      default: return '⏳ En cours';
    }
  }
}
