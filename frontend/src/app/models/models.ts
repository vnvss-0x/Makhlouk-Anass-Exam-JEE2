export interface Client {
  id?: number;
  nom: string;
  email: string;
}

export type StatutCredit = 'EN_COURS' | 'ACCEPTE' | 'REJETE';
export type TypeBien = 'APPARTEMENT' | 'MAISON' | 'LOCAL_COMMERCIAL';
export type TypeRemboursement = 'MENSUALITE' | 'REMBOURSEMENT_ANTICIPE';
export type TypeCredit = 'PERSONNEL' | 'IMMOBILIER' | 'PROFESSIONNEL';

export interface Credit {
  id?: number;
  dateDemande?: string;
  statut?: StatutCredit;
  dateAcceptation?: string;
  montant: number;
  dureeRemboursement: number;
  tauxInteret: number;
  clientId: number;
  typeCredit: TypeCredit;
}

export interface CreditPersonnel extends Credit {
  typeCredit: 'PERSONNEL';
  motif: string;
}

export interface CreditImmobilier extends Credit {
  typeCredit: 'IMMOBILIER';
  typeBien: TypeBien;
}

export interface CreditProfessionnel extends Credit {
  typeCredit: 'PROFESSIONNEL';
  motif: string;
  raisonSociale: string;
}

export interface Remboursement {
  id?: number;
  date: string;
  montant: number;
  type: TypeRemboursement;
  creditId: number;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  username: string;
  roles: string[];
}
