import React from 'react';

interface Step {
  id: number;
  label: string;
}

interface StepperProps {
  steps: Step[];
  currentStep: number;
  onStepClick: (id: number) => void; // Nova função para permitir o clique
}

const Stepper = ({ steps, currentStep, onStepClick }: StepperProps) => {
  return (
    <div 
      className="w-full overflow-x-auto py-4"
      // Esse estilo remove a barra de rolagem visualmente (Chrome, Safari, Firefox)
      style={{ scrollbarWidth: 'none', msOverflowStyle: 'none' }} 
    >
      <style>{`
        div::-webkit-scrollbar { display: none; }
      `}</style>

      <div className="flex items-start justify-between min-w-[600px] px-2">
        {steps.map((step, index) => {
          const isActive = step.id === currentStep;
          const isCompleted = step.id < currentStep;
          const isLast = index === steps.length - 1;

          return (
            <React.Fragment key={step.id}>
              
              {/* ITEM DO STEP (Bolinha + Texto) */}
              <div 
                onClick={() => onStepClick(step.id)} // Funcionalidade de clique
                className="relative flex flex-col items-center group cursor-pointer w-24 flex-shrink-0"
              >
                {/* Bolinha */}
                <div
                  className={`rounded-full h-10 w-10 flex items-center justify-center font-bold text-lg ring-4 transition-all duration-300 z-10
                  ${isActive 
                      ? 'bg-gray-700 ring-red-500 text-white shadow-lg shadow-red-500/50 scale-110' 
                      : isCompleted 
                          ? 'bg-red-600 ring-red-600 text-white hover:bg-red-500' 
                          : 'bg-gray-800 ring-gray-800 text-gray-500 group-hover:text-gray-300 group-hover:ring-gray-600'
                  }`}
                >
                  {step.id}
                </div>

                {/* Texto (Agora relativo, logo abaixo da bolinha) */}
                <span
                  className={`mt-3 text-xs font-bold uppercase text-center transition-colors duration-300 select-none
                  ${isActive ? 'text-white' : 'text-gray-500 group-hover:text-gray-400'}`}
                >
                  {step.label}
                </span>
              </div>

              {/* LINHA CONECTORA */}
              {!isLast && (
                <div className="flex-auto mt-5 h-[2px] bg-gray-700 mx-2 relative">
                  {/* Barra de progresso colorida que preenche a linha cinza */}
                  <div 
                    className={`absolute top-0 left-0 h-full bg-red-600 transition-all duration-500 ease-out`}
                    style={{ width: isCompleted ? '100%' : '0%' }}
                  ></div>
                </div>
              )}
            </React.Fragment>
          );
        })}
      </div>
    </div>
  );
};

export default Stepper;