package challenge;

import java.util.ArrayList;
import java.util.Optional;

public class Estacionamento {

    private ArrayList<Carro> vagas;
    final int LIMITE = 10;
    public Estacionamento() {
        this.vagas = new ArrayList<Carro>(LIMITE);
    }

    public void estacionar(Carro carro) {
        Carro obj = null;

        if(carro.getMotorista() != null) {
            validarFaixaEtariaMotorista(carro.getMotorista().getIdade());
                   Motorista motorista =  Motorista.builder()
                    .withPontos(carro.getMotorista().getPontos())
                    .withNome(carro.getMotorista().getNome())
                    .withHabilitacao(carro.getMotorista().getHabilitacao())
                    .withIdade(carro.getMotorista().getIdade())
                    .build();

            if(carro.getCor() != null) {
                obj = Carro.builder()
                        .withMotorista(motorista)
                        .withCor(carro.getCor())
                        .withPlaca(carro.getPlaca())
                        .build();
            }else {
                throw new NullPointerException(); }
        }else{
            throw new EstacionamentoException("Não pode ser carro Autonômo");
        }
        if(carrosEstacionados() < LIMITE){
            this.vagas.add(obj);
        }else{
            if(isMotoristaMaisNovo()){
                Carro remover = buscarMotoristaMaisNovo();
                this.vagas.remove(remover);
                this.vagas.add(obj);
            }else{
                throw new EstacionamentoException("Nao há vagas");
            }
        }

    }

    public int carrosEstacionados() {
        return this.vagas.size();

    }

    public boolean carroEstacionado(Carro carro) {
        return vagas.contains(carro);

    }

    public static void validarFaixaEtariaMotorista(int idade) {
        if (idade < 18 && idade >= 0) {
            throw new EstacionamentoException("Idade incompatível");
        }
    }
    public boolean isMotoristaMaisNovo(){
        return this.vagas.stream().anyMatch(t -> t.getMotorista().getIdade() < 55);
    }
    public Carro buscarMotoristaMaisNovo(){

        Optional<Carro> carroRemocao =  this.vagas.stream()
                .filter(t -> t.getMotorista().getIdade() < 55)
                .findFirst();
        return carroRemocao.get();
    }
}



