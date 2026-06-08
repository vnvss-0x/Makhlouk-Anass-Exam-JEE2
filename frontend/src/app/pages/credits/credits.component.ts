import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from '../../shared/navbar/navbar.component';
import { CreditService } from '../../services/credit.service';
import { Credit, StatutCredit } from '../../models/models';

@Component({
  selector: 'app-credits',
  standalone: true,
  imports: [CommonModule, NavbarComponent],
  templateUrl: './credits.component.html',
})
export class CreditsComponent implements OnInit {
  credits: Credit[] = [];
  filteredCredits: Credit[] = [];
  isLoading = true;
  activeFilter: StatutCredit | 'ALL' = 'ALL';

  constructor(private creditService: CreditService) {}

  ngOnInit(): void {
    this.loadCredits();
  }

  loadCredits(): void {
    this.isLoading = true;
    this.creditService.getAll().subscribe({
      next: (data: Credit[]) => {
        this.credits = data;
        this.applyFilter();
        this.isLoading = false;
      },
      error: () => { this.isLoading = false; }
    });
  }

  applyFilter(): void {
    if (this.activeFilter === 'ALL') {
      this.filteredCredits = this.credits;
    } else {
      this.filteredCredits = this.credits.filter(c => c.statut === this.activeFilter);
    }
  }

  setFilter(filter: StatutCredit | 'ALL'): void {
    this.activeFilter = filter;
    this.applyFilter();
  }

  updateStatut(id: number, statut: StatutCredit): void {
    this.creditService.updateStatut(id, statut).subscribe({
      next: () => this.loadCredits()
    });
  }

  getStatutClass(statut: StatutCredit | undefined): string {
    switch (statut) {
      case 'ACCEPTE': return 'bg-green-100 text-green-700';
      case 'REJETE': return 'bg-red-100 text-red-700';
      default: return 'bg-yellow-100 text-yellow-700';
    }
  }

  getTypeCreditClass(type: string | undefined): string {
    switch (type) {
      case 'IMMOBILIER': return 'bg-blue-100 text-blue-700';
      case 'PROFESSIONNEL': return 'bg-purple-100 text-purple-700';
      default: return 'bg-gray-100 text-gray-700';
    }
  }

  countByStatut(statut: StatutCredit): number {
    return this.credits.filter(c => c.statut === statut).length;
  }
}
