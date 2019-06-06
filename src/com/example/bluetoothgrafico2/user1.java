

package com.example.bluetoothgrafico2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Type;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class user1 extends Activity {
	public static Context context;
   
    private String dtext;//Valores para guardar a data de inicio
    private String dtext1;//Valores para guardar a data de inicio
    private String dtext2;//Valores para guardar a data de inicio
    private String dtext3;//Valores para guardar a data de inicio
    private String dtext4;//Valores para guardar a data de inicio
    private String dtext5;//Valores para guardar a data de inicio
    private String dtext6;//Valores para guardar a data de inicio
    private String dtext7;//Valores para guardar a data de inicio
    
    private String mtext;//Valores para guardar a massa dos usuários
    private String mtext1;//Valores para guardar a massa dos usuários
    private String mtext2;//Valores para guardar a massa dos usuários
    private String mtext3;//Valores para guardar a massa dos usuários
    private String mtext4;//Valores para guardar a massa dos usuários
    private String mtext5;//Valores para guardar a massa dos usuários
    private String mtext6;//Valores para guardar a massa dos usuários
    private String mtext7;//Valores para guardar a massa dos usuários
    
	static int qual_grafico=0;//Variavel que define se o gráfico é kcal,potência velocidade ou distância
	static int pot_data=0;//Tempo de seg em seg, ou min em minuto, hora em hora e dia em dia
	static int segundo_anterior=0;//Variavel auxiliar de tempo
	static int minuto_anterior=0;//Variavel auxiliar de tempo
	static int minuto_proximo=0;//Variavel auxiliar de tempo
	static int hora_anterior=0;//Variavel auxiliar de tempo
	static int hora_proximo=0;//Variavel auxiliar de tempo
	static int dia_anterior=0;//Variavel auxiliar de tempo
	static int dia_proximo=0;//Variavel auxiliar de tempo
	static double Volts=0;//Valor da tensão 
	static double amperes=0;//Valor da corrente 
	static double potencia=0;//Valor da potência(Calculada)
	static double[] massa=new double[7];//Valores de massa de cada usuário
	static double distancia=0;
	static double calorias=0;
	static double tempo_volta=0;//Tempo de uma volta
	static double distancia_volta=2.073;//Perimetro da roda em Metros(Constante)
	static double velocidade_cal=0;//Valor da velocidade de uma volta
	static double velocidade_soma=0;//Valor para calcular a velocidade média em uma período de tempo
	static int x;//Variavel contadora para calcular a média de velocidade
    static String Dataanterior="";//Valor auxiliar de tempo
    static String Datainicial="";//Data de inicio geral
   
    Button button_massa;//Valor auxiliar para o app detectar o botão para massa 
    EditText Edit_massa;//Valor auxiliar para o app detectar o Edit_text para massa
    TextView Massa_View;//Valor auxiliar para o app detectar o Text_View para massa
    //private static final String FILE_NAME = "grafico.txt";
    Button button_nome;//Valor auxiliar para o app detectar o botão para o nome do usuário 
    EditText Edit_nome;//Valor auxiliar para o app detectar o EditText para o nome do usuário 
    TextView nome_view;//Valor auxiliar para o app detectar o TextView para o nome do usuário 
    
    static Button kcal_button;//Valor auxiliar para o app detectar o Button kcal para gerar gráfico
    static TextView kcal_view;//Valor auxiliar para o app detectar o TextView  kcal para mostrar o valor kcal instântaneo
    
    static Button potencia_button;//Valor auxiliar para o app detectar o Button potência para gerar gráfico
    static TextView potencia_view;//Valor auxiliar para o app detectar o TextView  potência para mostrar o valor kcal instântaneo
    
    static Button velocidade_button;//Valor auxiliar para o app detectar o Button velocidade para gerar gráfico
    static TextView velocidade_view;//Valor auxiliar para o app detectar o TextView  velocidade para mostrar o valor kcal instântaneo
    
    static Button distancia_button;//Valor auxiliar para o app detectar o Button distância para gerar gráfico
    static TextView distancia_view;//Valor auxiliar para o app detectar o TextView  distância para mostrar o valor kcal instântaneo
    
    static Button resetar_button;
    @SuppressWarnings("unchecked")
	public static ArrayList<Double>[] kcal_array = new ArrayList[7]; //Cria 7 ArrayList com os dados de kcal
    @SuppressWarnings("unchecked")
	public static ArrayList<Double>[] potencia_array =   new ArrayList[7];//Cria 7 ArrayList com os dados de potência
    @SuppressWarnings("unchecked")
	public static ArrayList<Double>[] velocidade_array = new ArrayList[7];//Cria 7 ArrayList com os dados de velocidade
    @SuppressWarnings("unchecked")
	public static ArrayList<Double>[] distancia_array =  new ArrayList[7];//Cria 7 ArrayList com os dados de distância
    @SuppressWarnings("unchecked")
	public static ArrayList<String>[] date_array =       new ArrayList[7];//Cria 7 ArrayList com os dados de data de cada ponto
   
    public String loadData_inicia(){//Função usada carregar as datas inicias de cada usuário
    	SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
    	String nome="dtext";
    	int numero=MainActivity.usuario_numero+1;
    	nome=nome+String.valueOf(numero);
    	 dtext= sharedPreferences.getString(nome, "");
    
		return dtext;
		
    }
 
    public void saveData(String texto) {//Função usada guardar as nomes de cada usuário
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch(MainActivity.usuario_numero){
        case 0:
        editor.putString(MainActivity.nome1, texto);
        break;
        case 1:
            editor.putString(MainActivity.nome2, texto);
            break;
        case 2:
            editor.putString(MainActivity.nome3, texto);
            break;
        case 3:
            editor.putString(MainActivity.nome4, texto);
            break;
        case 4:
            editor.putString(MainActivity.nome5, texto);
            break;
        case 5:
            editor.putString(MainActivity.nome6, texto);
            break;
        case 6:
            editor.putString(MainActivity.nome7, texto);
            break;
        }
        editor.apply();
        Toast.makeText(this, "Nome salvo", Toast.LENGTH_SHORT).show();
    }
 
        
    
    public void saveMassa(String texto) {//Função usada para guardar a massa de cada usuário
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String nome="mtext";
    	int numero=MainActivity.usuario_numero+1;
    	nome=nome+String.valueOf(numero);
    	editor.putString(nome, texto);
        editor.apply();
        
    }
    
    public String loadMassa(){//Função usada para buscar qual a ultima massa setada do usuário
    	SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
    	String nome="mtext";
    	int numero=MainActivity.usuario_numero+1;
    	nome=nome+String.valueOf(numero);
    	mtext= sharedPreferences.getString(nome, "80.0");
    
		return mtext;
		
    }
    
    public void saveData_inicial(String texto) {//Função para Guardar a data inicial de um usuário
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String nome="dtext";
    	int numero=MainActivity.usuario_numero+1;
    	nome=nome+String.valueOf(numero);
    	editor.putString(nome, texto);
        editor.apply();
 
        
    }
    
  
    private static  void saveData_array(Context ctxt,int funcao) {//Guardar os dados de um ponto específico
    	//String text = mEditText.getText().toString();
        
    	FileOutputStream fos = null;//Cria um arquivo de texto com os valores à guardar
    	String nome_arquivo;//Nome do arquivo diferente para cada usuário
    	nome_arquivo="grafico"+String.valueOf(MainActivity.usuario_numero)+".txt";//formular para gerar o nome do arquivo em função do usuário
    	String dados;//Variável auxiliar para buscar cada dado
    	if(funcao==0){//Para o caso de escrever um novo dado no arquivo
        try {
            fos = ctxt.openFileOutput(nome_arquivo, MODE_APPEND);//Arquivo no formato txt
            int a=MainActivity.usuario_numero;//Variavel auxilar para detectar qual usuário 
	        int n=kcal_array[a].size()-1;//Qual o tamanho do arraylist sendo usado
	        if(n>0){//Se houver dados a ser guardado
	        dados="k"+kcal_array[a].get(n).toString()+"p"+potencia_array[a].get(n).toString()+"v"+velocidade_array[a].get(n).toString()+"d"+distancia_array[a].get(n).toString()+"f"+date_array[a].get(n)+"\n";
	        fos.write(dados.getBytes());//Guarda o arquivo
	        }
            
        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    	}
    	else{//Quando for limpar os dados
    		 try {
    	            fos = ctxt.openFileOutput(nome_arquivo, MODE_PRIVATE);//Cria um novo arquivo vazio com o nome
    		        }
    	      
    	         catch (FileNotFoundException e) {
    	            e.printStackTrace();
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        } finally {
    	            if (fos != null) {
    	                try {
    	                    fos.close();
    	                } catch (IOException e) {
    	                    e.printStackTrace();
    	                }
    	            }
    	        }
    	    }	
    }
 
    private  void loadData_array() {//Carregar os dados para um usuário
    	FileInputStream fis = null;//Leitura de arquivo
    	String nome_arquivo;//Váriavel auxiliar para gerar o nome do arquivo
    	nome_arquivo="grafico"+String.valueOf(MainActivity.usuario_numero)+".txt";//Fórmula para gerar o nome do arquivo conforme a usuário
    	double kcal_var;//Váriavel auxiliar
    	double potencia_var;//Váriavel auxiliar
    	double velocidade_var;//Váriavel auxiliar
    	double distancia_var;//Váriavel auxiliar
    	String data;//Váriavel auxiliar
    	int a;//Váriavel auxiliar
    	int b;//Váriavel auxiliar
        try {
            fis = openFileInput(nome_arquivo);//Abre o arquivo especifico
            InputStreamReader isr = new InputStreamReader(fis);//Abre o arquivo especifico
            BufferedReader br = new BufferedReader(isr);//Abre o arquivo especifico
            StringBuilder sb = new StringBuilder();//Abre o arquivo especifico
            String text;//Variavel para ler uma linha 
            int cont=0;
            while ((text = br.readLine()) != null) {//Enquanto houver linhas no arquivo
               // 
            	a=text.indexOf("k");//Se estiver em k e p
            	b=text.indexOf("p");//Se estiver em k e p
            	kcal_var=Double.parseDouble(text.substring(a+1,b));//Guarda como kcal
            	
            	a=text.indexOf("p");//Se estiver em p e v
            	b=text.indexOf("v");//Se estiver em p e v
            	potencia_var=Double.parseDouble(text.substring(a+1,b));//Guarda o como potencia
            	
            	a=text.indexOf("v");//Se estiver em v e d
            	b=text.indexOf("d");//Se estiver em v e d
            	velocidade_var=Double.parseDouble(text.substring(a+1,b)); //Guarda como velocidade
            	
            	a=text.indexOf("d");//Se estiver em d e f
            	b=text.indexOf("f");//Se estiver em d e f
            	distancia_var=Double.parseDouble(text.substring(a+1,b));//Guarda como distância
            	
            	data=text.substring(b+1);//O resto será guardado como data
            	
          
            	date_array[MainActivity.usuario_numero].add(data);//Guarda a data do ponto
    	        kcal_array[MainActivity.usuario_numero].add(kcal_var);//Guarda o valor de kcal 
    	        potencia_array[MainActivity.usuario_numero].add(potencia_var);//Guarda o valor de potencia 
    	        velocidade_array[MainActivity.usuario_numero].add(velocidade_var);//Guarda o valor de velocidade 
    	        distancia_array[MainActivity.usuario_numero].add(distancia_var);//Guarda o valor de distância
    	        
    	        cont+=1;//contagem é incrementada
            }
 
          
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
      
    }
	public void onCreate(Bundle savedInstanceState) {//Quando o layout é inicializado
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario1);
        
        Datainicial=loadData_inicia();//Busca o valor da primeira data guardada
        if(Datainicial.equals("")) {//Caso não haja valor de data 
        	Datainicial=hora_atual();//A datainicial é a hora atual
        	saveData_inicial(hora_atual());//Salva o valor da hora atual
        }
        for(int i =0; i<7;i++){//Criar 7 arrays para guardar os dados
        	
        	kcal_array[i]=new ArrayList<Double>(); 
        	potencia_array[i]=new ArrayList<Double>();
        	velocidade_array[i]=new ArrayList<Double>();
        	distancia_array[i]=new ArrayList<Double>();
        	date_array[i]=new ArrayList<String>();
        	kcal_array[i].add(0.0);//Ponto inicial sempre 0
        	potencia_array[i].add(0.0);//Ponto inicial sempre 0
        	velocidade_array[i].add(0.0);//Ponto inicial sempre 0
        	distancia_array[i].add(0.0);//Ponto inicial sempre 0
        	date_array[i].add(Datainicial);//Data inicial como calculada anteriormente
        	}
      
        context = this;//Usado para referênciar as funções que precisam de contexto
        loadData_array();//Carrega todos os dados guardados na memória e guarda no arraylists
        massa[MainActivity.usuario_numero]=Double.parseDouble(loadMassa());//Carrega o último valor de massa que o usuário registrou
       
        int n=date_array[MainActivity.usuario_numero].size();//Quantidade de dados que possui em cada array
        long tempo=calcular_segundos(Datainicial,date_array[MainActivity.usuario_numero].get(n-1));//Calcular quantos segundos ficou operando o equipamento
        if(tempo<60){//Caso seja menos de 60 segundos
        	pot_data=0;//O Gráfico mostra o tempo em seg e seg
        }
        else if(tempo<3600){//Caso seja menos de 3600 segundos
        	pot_data=1;//O Gráfico mostra o tempo em mim e mim
        }
        else if(tempo<86400){//Caso seja menos de 86400 segundos
        	pot_data=2;//O Gráfico mostra o tempo em hora e hora
        }
        else{//Caso seja maior que 86400
        	pot_data=3;//O Gráfico mostra o tempo em dia e dia
        }
      
      
        button_massa=(Button)findViewById(R.id.button2);
        Edit_massa=(EditText)findViewById(R.id.editText2);
        Massa_View=(TextView)findViewById(R.id.textView10);
        
        button_nome=(Button)findViewById(R.id.Button01);
        Edit_nome=(EditText)findViewById(R.id.EditText01);
        
        
        kcal_view=(TextView)findViewById(R.id.textView6);
        kcal_button=(Button)findViewById(R.id.button1);

        potencia_view=(TextView)findViewById(R.id.textView8);
        potencia_button=(Button)findViewById(R.id.button3);
        
        velocidade_view=(TextView)findViewById(R.id.textView4);
        velocidade_button=(Button)findViewById(R.id.button4);
        
        distancia_view=(TextView)findViewById(R.id.textView13);
        distancia_button=(Button)findViewById(R.id.button5);
        
        resetar_button=(Button)findViewById(R.id.button6);
        x=1;//Contador para o calculo da média de velocidade
       
        Massa_View.setText(massa[MainActivity.usuario_numero]+"Kg");//Valor de Massa setada pela usuário
        
        button_massa.setOnClickListener(new View.OnClickListener() {//Se for apertado o botão massa
            public void onClick(View arg0){
            	if(Edit_massa.getText().toString().equals("")){//Se não houver valor digitado 
            		massa[MainActivity.usuario_numero]=0;//Massa é zero
            		Massa_View.setText(massa[MainActivity.usuario_numero]+"Kg");//Massa é zero
            	}
            	else{//Se for digitado algum valor
            	
            	massa[MainActivity.usuario_numero]=Double.parseDouble(Edit_massa.getText().toString());//Massa é o valor digitado
            	Massa_View.setText(massa[MainActivity.usuario_numero]+"Kg");//massa é o valor digitado
            }
            	saveMassa(String.valueOf(massa[MainActivity.usuario_numero]));//Salva o valor de massa estabelecido
            }
            	});
        
        button_nome.setOnClickListener(new View.OnClickListener() {//Caso seja apertado o botão nome
            public void onClick(View arg0){
            	saveData(Edit_nome.getText().toString());//Guarda o nome como o nome do usuário
 
            	}
            	});
        
        kcal_button.setOnClickListener(new View.OnClickListener() {//Caso seja apertado o valor de kcal
            public void onClick(View arg0){
            	qual_grafico=0;//Define o gráfico como kcal
            	Intent trocando=new//troca o layout para mostrar o gráfico
            	Intent(user1.this,grafico.class);
            	user1.this.startActivity(trocando);
            	user1.this.finish();
            }		
            });
        potencia_button.setOnClickListener(new View.OnClickListener() {//Caso seja apertado o valor de potência
            public void onClick(View arg0){
            	qual_grafico=1;//Define o gráfico como potência
            	Intent trocando=new//troca o layout para mostrar o gráfico
            	Intent(user1.this,grafico.class);
            	user1.this.startActivity(trocando);
            	user1.this.finish();
            }		
            });
        velocidade_button.setOnClickListener(new View.OnClickListener() {//Caso seja apertado o valor de velocidade
            public void onClick(View arg0){
            	qual_grafico=2;//Define o gráfico como velocidade
            	Intent trocando=new//troca o layout para mostrar o gráfico
            	Intent(user1.this,grafico.class);
            	user1.this.startActivity(trocando);
            	user1.this.finish();
            }		
            });
        distancia_button.setOnClickListener(new View.OnClickListener() {//Caso seja apertado o valor de distância
            public void onClick(View arg0){
            	qual_grafico=3;//Define o gráfico como distância
            	Intent trocando=new//troca o layout para mostrar o gráfico
            	Intent(user1.this,grafico.class);
            	user1.this.startActivity(trocando);
            	user1.this.finish();
            }		
            });
        
        resetar_button.setOnClickListener(new View.OnClickListener() {//Caso queira resetar
            public void onClick(View arg0){
            	AlertDialog.Builder builder = new AlertDialog.Builder(user1.this);
            	String nome="";
        		switch(MainActivity.usuario_numero){//Achar qual o nome do usuário
        		case 0:
        			nome=(String) MainActivity.usuarior1.getText();
        		break;
        		case 1:
        			nome=(String) MainActivity.usuarior2.getText();
        		break;
        		case 2:
        			nome=(String) MainActivity.usuarior3.getText();
        		break;
        		case 3:
        			nome=(String) MainActivity.usuarior4.getText();
        		break;
        		case 4:
        			nome=(String) MainActivity.usuarior5.getText();
        		break;
        		case 5:
        			nome=(String) MainActivity.usuarior6.getText();
        		break;
        		case 6:
        			nome=(String) MainActivity.usuarior7.getText();
        		break;
        		}
            	builder.setTitle("Reiniciar");
            	builder.setMessage("Você tem certeza que deseja reiniciar os dados de "+nome+" ?");
            	
            	builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {//Se for selecionada a opção "sim"

            	    public void onClick(DialogInterface dialog, int which) {
            	    	//Limpa o array e depois fecha o dialogo
            	    	limpar_arrays();
            	    	Datainicial=hora_atual();//A datainicial é a hora atual
                    	saveData_inicial(hora_atual());//Salva o valor da hora atual
                    	
                    	int n=MainActivity.usuario_numero+1;
                    	saveData("Usuário"+n);//Reinicia também o nome
                    	
            	        dialog.dismiss();
            	    }
            	});

            	builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {//Se for selecionada a opção "não"

            	    @Override
            	    public void onClick(DialogInterface dialog, int which) {
            	    	//Não faz nada e fecha o dialogo
            	        dialog.dismiss();
            	    }
            	});

            	AlertDialog alert = builder.create();
            	alert.show();
            }		
            });
        
    }
        
	//}
	
	public void onBackPressed() {//Se for apertado o valor para retornar 
		super.onBackPressed();//Volta para o menu principal
		Intent trocando=new//Volta para o menu principal
        Intent(user1.this,MainActivity.class);//Volta para o menu principal
        user1.this.startActivity(trocando);//Volta para o menu principal
        user1.this.finish();//Volta para o menu principal
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            onBackPressed();
	            return true;
	    }

	    return(super.onOptionsItemSelected(item));
	}
	 public static Handler handler = new Handler() {//Essa classe é acionada quando o Bluetooth envia um sinal
	        @Override
	        public void handleMessage(Message msg) {//Recebe a mensagem dada pelo Bluetooth
	           Bundle bundle = msg.getData();
	           byte[] data = bundle.getByteArray("data");    
	           String leitura = new String(data); //Leitura é o valor lido
	           
	           try{//Usado para evitar erros de conversão
	           if(leitura.contains("v"))//Se houver v na leitura
	           {   
	          
	           Volts =Double.parseDouble(leitura.substring(1));//Passa a informação para variável volts          
	           }
	           if(leitura.contains("i")){//Se houver i na leitura
		     
		       amperes=Double.parseDouble(leitura.substring(1));//Passa a informação para a variável amperes
	           }
	           
	           if(leitura.contains("t")){//Se houver t na leitura
	           
	           tempo_volta=Double.parseDouble(leitura.substring(1));//Passa a informação da leitura para a variável tempo_volta
	           if(Volts*amperes/1000>0) potencia+=Volts*amperes/1000;
	           distancia+=distancia_volta;//Calculo da distância acumulada
	           calorias+=calculo_caloria(tempo_volta);//Calculo do gasto calorico acumulado
	           velocidade_cal=distancia_volta*100/(tempo_volta);//Calculo da velocidade de uma volta
	           
	           if(potencia<0){//Potência não pode ser menor que zero
	        	   potencia=0;
	        	   
	           }
	           if(calorias<0){//Calorias não pode ser menor que zero
	        	   calorias=0;
	           }
	          if(velocidade_cal<0){//Velocidade não pode ser menor que zero
	        	  velocidade_cal=0;
	          }
	          
	           //Guardar os valores no array
	           switch (pot_data){
	           case 0://De 0 a 60segundos
	           
	              
		       String reportDate = hora_atual();//Qual a data atual
		       
		       if(!Dataanterior.equals(reportDate)){//Se houver alguma mudança nos segundos
		    	   
		       adicionar_arrays(reportDate,calorias,potencia,velocidade_cal,distancia/1000);  //Grava os velores no Arraylist
		     
	           velocidade_soma=+velocidade_cal;//Soma a velocidade 
	           
	           segundo_anterior=(int) calcular_segundos(Datainicial,reportDate);//Quantos segundos passaram
	           mudar_text(calorias,potencia,velocidade_cal,distancia);//Muda os valores do TextView do Layout
	           
	           x+=1;//Valor usado para calcular a média da velocidade
		           if(segundo_anterior>=60){//Se tiver passado 60segundos
		        	 pot_data=1;//Muda a forma de mostrar os dados para min em min
			         velocidade_soma=0;
			         x=0;
		           }
		       }

	           Dataanterior=reportDate;//Guarda a data do último ponto
	          
	           break;
	           case 1://De 0 a 60minutos    
			       String reportDate1 = hora_atual();
			       
			       if(!Dataanterior.equals(reportDate1)){//Caso haja uma diferença nos segundos
			    	   
			       velocidade_soma=+velocidade_cal;//Valor usado para calculo da média da velocidade no instante
			       minuto_anterior=(int) calcular_minutos(Datainicial,reportDate1);//Calculo de quantos minutos se passaram
		           mudar_text(calorias,potencia,velocidade_cal,distancia);//Muda os TextView do Layout
		           adicionar_arrays(reportDate1,calorias,potencia,velocidade_cal,distancia/1000);  //Guarda os valores obtidos nesse ponto
		           x+=1;//Valor usado para calculo da média da velocidade no instante
		           
			           if(minuto_anterior>=60){//Caso tenham passado mais de 60 minutos 
			        	   limpar_arrays();//Apagar os valores obtidos até esse ponto
			        	   adicionar_arrays(reportDate1,calorias,potencia,velocidade_soma/x,distancia/1000);//Guardar a média da hora
			        	   velocidade_soma=0;//Valor usado para calculo da média da velocidade no instante
			        	   minuto_anterior=0;//Valor usado para calculo da média da velocidade no instante
			        	   pot_data=2;//Muda o valor para hora em hora
			           }
			    
			       }

		           Dataanterior=reportDate1;//Guarda o último valor de data
	           break;
	           
	           case 2://De 0 a 24horas    
			       String reportDate2 = hora_atual();
			       
			       if(!Dataanterior.equals(reportDate2)){//Em caso haja uma diferença no segundo
			    	   
			      minuto_anterior=(int) calcular_minutos(Datainicial,reportDate2);//Calculo de quanto minutos se passaram
			      hora_anterior=(int) calcular_hora(Datainicial,reportDate2);//Calculo de quantas horas se passaram
		          mudar_text(calorias,potencia,velocidade_cal,distancia);//Muda os valores de TextView no Layout
			    
		           x+=1;//Valor usado para calculo da média da velocidade no instante
		           velocidade_soma+=velocidade_cal;//Valor usado para calculo da média da velocidade no instante
		          
			           if(minuto_anterior!=minuto_proximo){//Caso passe mais de um minuto
			        	   adicionar_arrays(reportDate2,calorias,potencia,velocidade_soma/x,distancia/1000);//Registro dos valores obtidos no minuto
				           velocidade_soma=0;//Valor usado para calculo da média da velocidade no instante
				           x=0;//Valor usado para calculo da média da velocidade no instante
			           }
		         
			           if(hora_anterior>=24){//Caso tenham passado horas
			        	   limpar_arrays();//Limpar os valores obtidos até o momento
			        	   adicionar_arrays(reportDate2,calorias,potencia,velocidade_soma,distancia/1000);//registro dos valores obtidos no dia
			        	   velocidade_soma=0;//Valor usado para calculo da média da velocidade no instante
			        	   minuto_anterior=0;////Valor usado calculo do tempo
			        	   pot_data=3;//Muda o valor para dia em dia
			           }
			           
			       minuto_proximo=minuto_anterior;//Guarda o último valor de tempo
			       
			       }

		           Dataanterior=reportDate2;//Guarda o último valor de data
	           break;
	           
	           case 3://Tempo em dias    
			       String reportDate3 = hora_atual();
			       
			       if(!Dataanterior.equals(reportDate3)){//Caso haja uma mudança nos segundos
			    	   
			      hora_anterior=(int) calcular_hora(Datainicial,reportDate3);//Calculo de quantas horas passaram
			      dia_anterior=(int) calcular_dia(Datainicial,reportDate3);//Calculo de quantos dias passaram
		          mudar_text(calorias,potencia,velocidade_cal,distancia);//Atualiza os TexTViews no Layout
		          x+=1;//Variável para cálculo da velocidade média em um instante
		           velocidade_soma+=velocidade_cal;//Variável para cálculo da velocidade média em um instante
			           if(hora_anterior!=hora_proximo){//Caso tenha passado uma hora
			        	   adicionar_arrays(reportDate3,calorias,potencia,velocidade_soma/x,distancia/1000);//Adicionar ponto no ArrayList
				           velocidade_soma=0;//Variável para cálculo da velocidade média em um instante
				           x=0;//Variável para cálculo da velocidade média em um instante
			           }
		        
			       hora_proximo=hora_anterior; //Registro do último tempo
			       
			       }

		           Dataanterior=reportDate3;//Registro da última data
	           break;
	           }
	           
	           }
	           }
	           catch(NumberFormatException ex){//Caso tenha algum erro de conversão
	        	   //Fica inoperante
	           }
	        }
	   
	        
	    };
	    
		private static long calcular_dia(String datainicial, String reportDate3) {//Número de dias entre duas datas
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		     String dateInString = datainicial;
		     java.util.Date date = null;
		     java.util.Date date1 = null;
		      try {
				date = formatter.parse(dateInString);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		      SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			     String dateInString1 = reportDate3;
			      try {
					 date1 = formatter1.parse(dateInString1);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			      long seconds = (-date.getTime()+date1.getTime())/1000;
		         
			return seconds/86400;
		}
		private static long calcular_hora(String datainicial, String reportDate2) {//Número de horas entre duas datas 
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		     String dateInString = datainicial;
		     java.util.Date date = null;
		     java.util.Date date1 = null;
		      try {
				date = formatter.parse(dateInString);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		      SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			     String dateInString1 = reportDate2;
			      try {
					 date1 = formatter1.parse(dateInString1);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			      long seconds = (-date.getTime()+date1.getTime())/1000;
		         
			return seconds/3600;
		}
		
		private static long calcular_minutos(String datainicial, String reportDate1) {//Número de minutos entre duas datas
			 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		     String dateInString = datainicial;
		     java.util.Date date = null;
		     java.util.Date date1 = null;
		      try {
				date = formatter.parse(dateInString);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		      SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			     String dateInString1 = reportDate1;
			      try {
					 date1 = formatter1.parse(dateInString1);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			      long seconds = (-date.getTime()+date1.getTime())/1000;
		         
			return seconds/60;
		}
	    private static long calcular_segundos(String datainicial, String reportDate) {//Número de segundos entre duas datas
			 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		     String dateInString = datainicial;
		     java.util.Date date = null;
		     java.util.Date date1 = null;
		      try {
				date = formatter.parse(dateInString);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      
		      SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			     String dateInString1 = reportDate;
			      try {
					 date1 = formatter1.parse(dateInString1);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			      long seconds = (-date.getTime()+date1.getTime())/1000;
		         
			return seconds;
		}
		private static double calculo_caloria(double tempo_volta) {//Calculo de calorias conforme o tempo de uma volta
			double velocidade=distancia_volta*100/(tempo_volta);
			double indice;
			velocidade=velocidade/3.6;
			if(velocidade<=16){
				indice=0.001166667;
			}else if(velocidade<=19){
				indice=0.00175;
			}else if(velocidade<=22){
				indice=0.00233333;
				
			}else if(velocidade<=25){
				indice=0.002916667
;
			}else if(velocidade<=32){
				indice=0.0035;
			}else {
				indice=0.004666667;
			}
			
			return indice*massa[MainActivity.usuario_numero]*tempo_volta/100;
		}
	 

		private static String hora_atual() {//Calculo da hora_atual
			SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    java.util.Date today1 =  Calendar.getInstance().getTime();        
			return df1.format(today1);
		}

		private static void mudar_text(double calorias, double potencia,
				double velocidade_cal, double distancia) {//Mudar o Text_View dos pontos
			// TODO Auto-generated method stub
			 kcal_view.setText(Double.toString(Math.round(calorias*100.0)/100.0)+"kcal");
	         potencia_view.setText(Double.toString(Math.round(potencia*100.0)/100.0)+"W");
	         velocidade_view.setText(Double.toString(Math.round(velocidade_cal*100.0)/100.0)+"km/h");
	         distancia_view.setText(Double.toString(Math.round(distancia*0.1)/100.0)+"km");
			
		}

		private static void adicionar_arrays(String reportDate, Double calorias, double potencia, double d, double e) {//Adicionar pontos os ArrayList
	        kcal_array[MainActivity.usuario_numero].add(calorias);
	        potencia_array[MainActivity.usuario_numero].add(potencia);
	        velocidade_array[MainActivity.usuario_numero].add(d);
	        distancia_array[MainActivity.usuario_numero].add(e);
	        date_array[MainActivity.usuario_numero].add(reportDate);
	        saveData_array(context,0);//Salva no arquivo
	       
		}

		private static void limpar_arrays() {//Limpar os valores obtidos até o momento
			
			 date_array[MainActivity.usuario_numero].clear();
			 kcal_array[MainActivity.usuario_numero].clear();
        	 potencia_array[MainActivity.usuario_numero].clear();
        	 velocidade_array[MainActivity.usuario_numero].clear();
        	 distancia_array[MainActivity.usuario_numero].clear();
        	 saveData_array(context,1);//Recomeça o arquivo
        	kcal_array[MainActivity.usuario_numero].add(0.0);
         	potencia_array[MainActivity.usuario_numero].add(0.0);
         	velocidade_array[MainActivity.usuario_numero].add(0.0);
         	distancia_array[MainActivity.usuario_numero].add(0.0);
         	date_array[MainActivity.usuario_numero].add(Datainicial);
        	 
		}
}




