// src/data/mockData.ts

export const TALENTOS = [
  { value: 'sortudo', label: 'Sortudo', desc: 'Você tem 3 pontos de sorte. Sempre que fizer uma jogada de ataque, teste de habilidade ou teste de resistência, você pode gastar um ponto de sorte para jogar um d20 adicional.' },
  { value: 'curandeiro', label: 'Curandeiro', desc: 'Quando você usa um kit de curandeiro para estabilizar uma criatura moribunda, essa criatura também recupera 1 ponto de vida.' },
  { value: 'atleta', label: 'Atleta', desc: 'Sua Força ou Destreza aumenta em 1, até o máximo de 20. Levantar-se de estar deitado custa apenas 1,5m de deslocamento.' },
];

export const TRUQUES = [
  { value: 'luz', label: 'Luz', desc: 'Você toca um objeto e ele emite luz plena num raio de 6m e penumbra por mais 6m.' },
  { value: 'maos_magicas', label: 'Mãos Mágicas', desc: 'Uma mão espectral flutuante aparece num ponto à sua escolha. Você pode usar a mão para manipular objetos.' },
  { value: 'rajada_mistica', label: 'Rajada Mística', desc: 'Um feixe de energia crepitante é disparado contra uma criatura ao alcance. Faça um ataque mágico à distância.' },
];

export const MAGIAS = [
  { value: 'missil_magico', label: 'Míssil Mágico', desc: 'Você cria três dardos brilhantes de força mágica. Cada dardo atinge uma criatura que você possa ver.' },
  { value: 'curar_ferimentos', label: 'Curar Ferimentos', desc: 'Uma criatura que você tocar recupera pontos de vida iguais a 1d8 + seu modificador de habilidade de conjuração.' },
  { value: 'escudo', label: 'Escudo', desc: 'Uma barreira invisível de força mágica aparece e protege você. Até o início do seu próximo turno, você tem +5 na CA.' },
];

export const PERICIAS = [
  { value: 'acrobacia', label: 'Acrobacia', desc: 'Mede sua capacidade de se manter em pé em situações complicadas.' },
  { value: 'furtividade', label: 'Furtividade', desc: 'Mede sua habilidade de se esconder e mover-se silenciosamente.' },
  { value: 'intimidacao', label: 'Intimidação', desc: 'Quando você tenta influenciar alguém através de ameaças, ações hostis ou violência física.' },
];