import { Routes } from '@angular/router';
import { authGuard, employeGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'credits',
    canActivate: [employeGuard],
    loadComponent: () => import('./pages/credits/credits.component').then(m => m.CreditsComponent)
  },
  {
    path: 'clients',
    canActivate: [employeGuard],
    loadComponent: () => import('./pages/clients/clients.component').then(m => m.ClientsComponent)
  },
  {
    path: 'mes-credits',
    canActivate: [authGuard],
    loadComponent: () => import('./pages/mes-credits/mes-credits.component').then(m => m.MesCreditsComponent)
  },
  { path: '**', redirectTo: '/login' }
];
