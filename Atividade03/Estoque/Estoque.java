import java.util.ArrayList;
import java.util.Date;

public class Estoque {
    private ArrayList<Produto> produtos = new ArrayList<Produto>();
    //private Produto[] produtos = new Produto[100];
    private int quant = 0;

    //Método auxiliar para pesquisar produtos
    public Produto pesquisar(int cod){
        for (Produto p: produtos){
            if (p.getCodigo() == cod){
                return p;
            }
        }
        return null;
    }

    //Método para Incluir produtos
    public boolean incluir(Produto p){
        if (p == null || p.getCodigo() < 0 || p.getFornecedor().getCnpj() < 0 || p.getFornecedor().getCnpj() == 0 || p.getDescricao().trim().isEmpty()){
            return false;
        }
        Produto prod = this.pesquisar(p.getCodigo());
        if (prod  == null){
            this.produtos.add(p);
            //this.produtos[this.quant] = p;
            this.quant++;
            return true;
        }
        return false;
    }

    ///Método para ver a quantidade de produtos
    public int quantidade(int cod){
        Produto prod = pesquisar(cod);
        if (prod != null){
            return prod.getQuant();
        }
        return 0;
    }

    //Método para vender um produto
    public double vender(int cod, int quant){
        Produto prod = pesquisar(cod);
        if (prod == null){
            return -1;
        }
        return prod.venda(quant);
    }

    //Método para comprar um produto
    public boolean comprar(int cod, int quant, double preco, Date data){
        Produto prod = pesquisar(cod);
        if (prod != null){
            if (data == null){
                return prod.compra(quant, preco);
            }else{
                if (prod instanceof ProdutoPerecivel){
                    return ((ProdutoPerecivel)prod).compra(quant, preco, data);
                }
                return false;
                
            }
        }
        return false;
    }

    //Método para pesquisar um fornecedor
    public Fornecedor fornecedor(int cod){
        Produto prod = pesquisar(cod);
        if (prod != null){
            return prod.getFornecedor();
        }
        return null;
    }

    public ArrayList<Produto> estoqueAbaixoDoMinimo(){
        ArrayList<Produto> abaixoDoMinimo = new ArrayList<Produto>(); 
        for (Produto p: produtos){
            if (p.abaixoDoMinimo()){
                abaixoDoMinimo.add(p);
            }
        }
        return abaixoDoMinimo;
    }

    public ArrayList<Produto> estoqueVencido(){
        ArrayList<Produto> vencidos = new ArrayList<Produto>();
        for (Produto  p: produtos){
            if (p instanceof ProdutoPerecivel){
                if (((ProdutoPerecivel)p).produtoVencido()){
                    vencidos.add(p);
                }
            }
        }
        return vencidos;
    }
}
