// src/types/Magia.ts

// Enum gera um objeto JS real, por isso não pode estar em .d.ts
export enum EscolaDeMagia {
  ABJURACAO = "ABJURACAO",
  ADIVINHACAO = "ADIVINHACAO",
  CONJURACAO = "CONJURACAO",
  ENCANTAMENTO = "ENCANTAMENTO",
  EVOCACAO = "EVOCACAO",
  ILUSAO = "ILUSAO",
  NECROMANCIA = "NECROMANCIA",
  TRANSMUTACAO = "TRANSMUTACAO"
}

// Interface é apagada na compilação, serve só pra checagem
export interface MagiaDTO {
  id?: number;
  nome: string;
  circulo: number;
  escola?: EscolaDeMagia | null; 
  tempoConjuracao: string;
  alcance: string;
  componentes: string;
  duracao: string;
  isConcentracao: boolean;
  isRitual: boolean;
  formulaDano?: string | null;
  tipoDano?: string | null;
  descricao: string;
  usuarioId: number;
}