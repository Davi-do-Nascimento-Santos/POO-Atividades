package banco;
import java.util.Date;
import java.text.SimpleDateFormat;
public class ContaComum {
	
	private int num;
	private double saldo;
	private Extrato[] extrato = new Extrato[100];
	private int pos = 0;
	private Pessoa dono;

	
	public ContaComum(int n, Pessoa p) {
	   num = n;	
	   if (p.getConta() == null) {
	     dono = p;
	     p.setConta(this);
	   } else {
		 System.out.println("Essa pessoa já possui uma conta");
		 return;
	   }
	}
	
	public void credito(double val) {
		saldo = saldo + val;
		Date date = new Date();
		extrato[pos++] = new Extrato("Credito", val, saldo, date);
	}
	
	public void debito(double val) {
		if (val <= saldo) { 
		  	saldo = saldo - val;
			Date date = new Date();
			extrato[pos++] = new Extrato("Debito", val, saldo, date);
		} else {
			System.out.println("Saldo insuficiente.");
		}
	}

	public int getNum() {
		return num;
	}
	
	public void setNum(int n) {
		if (n < 1000) { 
		  num = n;
		} else {
			System.out.println("Não é permitido usar numeros maiores que 1000");
		}
	}

	public double getSaldo() {
		return saldo;
	}

	public String getExtrato() {
		String ext = "";
		for (int i = 0; i < pos; i++) {
			ext = ext + extrato[i].getLinha();
		}
		return ext;
	}

	public String getExtrato(Date di, Date df) {
		String ext = "";
		for (int i = 0; i < pos; i++) {
			ext = ext + extrato[i].getLinha();
		}
		return ext;
	}

	public Boolean dataMenor(int[] di, int[] da){
		if (di[2] <= da[2]){
			if (di[1] <= da[1]){
				if (di[0] <= da[0]){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public Pessoa getDono() {
		return dono;
	}
	
}