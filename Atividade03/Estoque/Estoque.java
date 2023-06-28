import java.util.ArrayList;
import java.util.Date;

public class Estoque implements InterfaceEstoqueComExcecoes{
    private ArrayList<Produto> produtos = new ArrayList<Produto>();
    //private Produto[] produtos = new Produto[100];
    private int quant = 0;

    //Método auxiliar para pesquisar produtos
    public Produto pesquisar(int cod) throws ProdutoInexistente{
        for (Produto p: produtos){
            if (p.getCodigo() == cod){
                return p;
            }
        }
        throw new ProdutoInexistente();
    }

    //Método para Incluir produtos
    public void incluir(Produto p) throws ProdutoJaCadastrado, DadosInvalidos{
        if (p == null || p.getCodigo() < 0 || p.getFornecedor().getCnpj() < 0 || p.getFornecedor().getCnpj() == 0 || p.getDescricao().trim().isEmpty()){
            throw new DadosInvalidos();
        }
        try{
            Produto prod = this.pesquisar(p.getCodigo());
            if (prod  == null){
            this.produtos.add(p);
            //this.produtos[this.quant] = p;
            this.quant++;
            } else{
                throw new ProdutoJaCadastrado(p.getCodigo());
            }
        } catch (ProdutoInexistente e){
            System.out.println(e.getMessage());

        }
    }
        

    ///Método para ver a quantidade de produtos
    public int quantidade(int cod) throws ProdutoInexistente{
        Produto prod = pesquisar(cod);
        if (prod != null){
            return prod.getQuant();
        }
        return 0;
    }

    //Método para vender um produto
    public double vender(int cod, int quant) throws ProdutoInexistente, ProdutoVencido{
        Produto prod = pesquisar(cod);
        if (prod == null){
            throw new ProdutoInexistente();
        }
        if (prod instanceof ProdutoPerecivel){
            return ((ProdutoPerecivel)prod).venda(quant);
        }
        return prod.venda(quant);
    }

    //Método para comprar um produto
    public void comprar(int cod, int quant, double preco, Date val) throws ProdutoInexistente, DadosInvalidos, ProdutoNaoPerecivel {
        if (cod < 0 || quant <= 0 || preco <= 0){
            throw new DadosInvalidos();
        }   else{
            Produto prod = pesquisar(cod);
            if (prod != null){
                if (val == null){
                    prod.compra(quant, preco);
                }else{
                    if (prod instanceof ProdutoPerecivel){
                        ((ProdutoPerecivel)prod).compra(quant, preco, val);
                    } else{
                        throw new ProdutoNaoPerecivel();
                    }
                }
            } else{
                throw new ProdutoInexistente();
            }
        }
    }

    //Método para pesquisar um fornecedor
    public Fornecedor fornecedor(int cod) throws ProdutoInexistente{
        Produto prod = pesquisar(cod);
        if (prod != null){
            return prod.getFornecedor();
        }
        return null;
    }

    public ArrayList<Produto> estoqueAbaixoDoMinimo(){
        ArrayList<Produto> abaixoDoMinimo = new ArrayList<Produto>(); 
        for (Produto p: produtos){
            if (p instanceof ProdutoPerecivel){
                if (((ProdutoPerecivel)p).abaixoDoMinimo()){
                    abaixoDoMinimo.add(p);
                }
            }else if (p instanceof Produto){
                if (p.abaixoDoMinimo()){
                    abaixoDoMinimo.add(p);
                }
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

    public int quantidadeVencidos(int cod) throws ProdutoInexistente {
        Produto prod = pesquisar(cod);
        return ((ProdutoPerecivel)prod).quantideVencidos();
    }
}
