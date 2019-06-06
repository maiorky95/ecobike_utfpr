package com.example.bluetoothgrafico2;

import java.text.SimpleDateFormat;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

public class grafico extends Activity { 
	static LineGraphSeries<DataPoint> series;
	static GraphView graph;
	static int x;
	String nome_do_grafico="";
	Double valorx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {//Quando iniciar o Layout
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grafico);
		    graph = (GraphView) findViewById(R.id.graph);//Variável auxiliar para o app detectar o graph 
	   
	        graph.getViewport().setYAxisBoundsManual(true);
	        graph.getViewport().setMinY(0);
	        

	        graph.getViewport().setXAxisBoundsManual(true);
	        graph.getViewport().setMinX(0);
	        graph.getViewport().setMaxX(60);
	        graph.getViewport().setScrollable(true); // enables horizontal scrolling
	        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
	        
	        graph.getGridLabelRenderer().setNumVerticalLabels(10);
	        graph.getGridLabelRenderer().setNumHorizontalLabels(5);
	        
	        series = new LineGraphSeries<DataPoint>();//Novos pontos para a série
	        series.setDrawDataPoints(false);//Pontos explcitos 
 	        series.setDataPointsRadius(10);//Tamanhos do circulo do ponto
 	        series.setThickness(4);//Expessura da linha do gráfico
	       
	        int n =0;//Calculo do tamanhos dos arraylists
	        nome_do_grafico=nome_do_grafico();//Nome do gráfico conforme o tipo
	        graph.setTitle(nome_do_grafico);//Muda nome do gráfico
	        
	      
	        
	        
	        switch (user1.qual_grafico){//Conforme o gráfico selecionado
	        case 0://Kcal
	        	double maior=0;
	        	for(int a=0;a<user1.kcal_array[MainActivity.usuario_numero].size();a++){//Acha o maior valor da série de dados
	        		if(user1.kcal_array[MainActivity.usuario_numero].get(a)>maior)
	        			maior=user1.kcal_array[MainActivity.usuario_numero].get(a);
	        	}
	        	maior=Math.round(maior/10);//Acha o mais próximo multiplo de 10 do valor 
	        	maior=maior*10+10;//Acha o mais próximo multiplo de 10 do valor
	        	graph.getViewport().setMaxY(maior);//Acha o mais próximo multiplo de 10 do valor
	        	
	 	        series.setColor(Color.RED);//Cor vermelha
	 	        series.setTitle("Segundos");//Mudança no nome da série
	        	n=user1.kcal_array[MainActivity.usuario_numero].size();//Tamanho dos ArrayList
		        for(int i=0;i<n;i++){//Plotar os pontos
		        valorx=achar_x(i);
		        series.appendData(new DataPoint(valorx,user1.kcal_array[MainActivity.usuario_numero].get(i)), true, 3600);
		        }
		        
		        graph.addSeries(series);
	        break;
	        case 1://potência
	        	double maiorp=0;
	        	for(int a=0;a<user1.potencia_array[MainActivity.usuario_numero].size();a++){//Acha o maior valor da série de dados
	        		if(user1.potencia_array[MainActivity.usuario_numero].get(a)>maiorp)
	        			maiorp=user1.potencia_array[MainActivity.usuario_numero].get(a);
	        	}
	        	maiorp=Math.round(maiorp/10);//Acha o mais próximo multiplo de 10 do valor 
	        	maiorp=maiorp*10+10;//Acha o mais próximo multiplo de 10 do valor
	        	
	        	graph.getViewport().setMaxY(maiorp);//Valor máximo de potência
	        	
	 	        series.setColor(Color.BLUE);//Cor Azul
	        	n=user1.potencia_array[MainActivity.usuario_numero].size();//Tamanho dos ArrayList
		        for(int i=0;i<n;i++){//Plotar os pontos
		        	valorx=achar_x(i);
		        series.appendData(new DataPoint(valorx,user1.potencia_array[MainActivity.usuario_numero].get(i)), true, 3600);
		       
		        }
		        graph.addSeries(series);
	        	
	        break;
	        case 2://Velocidade
	        	double maiorv=0;
	        	for(int a=0;a<user1.velocidade_array[MainActivity.usuario_numero].size();a++){//Acha o maior valor da série de dados
	        		if(user1.velocidade_array[MainActivity.usuario_numero].get(a)>maiorv)
	        			maiorv=user1.velocidade_array[MainActivity.usuario_numero].get(a);
	        	}
	        	maiorv=Math.round(maiorv/10);//Acha o mais próximo multiplo de 10 do valor 
	        	maiorv=maiorv*10+10;//Acha o mais próximo multiplo de 10 do valor
	        	
	        	graph.getViewport().setMaxY(maiorv);
	        	
	    
	 	        series.setColor(Color.GREEN);//Cor Verde
	        	n=user1.velocidade_array[MainActivity.usuario_numero].size();//Tamanho dos ArrayList
	        	
	        	double soma_velocidade=0;
	        	double media_velocidade=0;
	        	for(int q=0;q<user1.velocidade_array[MainActivity.usuario_numero].size();q++){
	        	soma_velocidade+=user1.velocidade_array[MainActivity.usuario_numero].get(q);
	        	
	        	}
	        	media_velocidade=soma_velocidade/user1.velocidade_array[MainActivity.usuario_numero].size();
	        
		        for(int i=0;i<n;i++){//Plotar os pontos
		        	valorx=achar_x(i);
		        
		        //series.appendData(new DataPoint(valorx,user1.velocidade_array[MainActivity.usuario_numero].get(i)), true, 3600);
		        	series.appendData(new DataPoint(valorx,media_velocidade), true, 3600);
		        }
		        graph.addSeries(series);
	        	
	        break;
	        case 3://Distância
	        	double maiord=0;
	        	for(int a=0;a<user1.distancia_array[MainActivity.usuario_numero].size();a++){//Acha o maior valor da série de dados
	        		if(user1.distancia_array[MainActivity.usuario_numero].get(a)>maiord)
	        			maiord=user1.distancia_array[MainActivity.usuario_numero].get(a);
	        	}
	        	maiord=Math.round(maiord/2);//Acha o mais próximo multiplo de 10 do valor 
	        	maiord=maiord*2+2;//Acha o mais próximo multiplo de 10 do valor
	        	
	        	graph.getViewport().setMaxY(maiord);
	        	
	        	
	 	        series.setColor(Color.BLUE);//Cor azul
	        	n=user1.distancia_array[MainActivity.usuario_numero].size();//Tamanho dos ArrayList
		        for(int i=0;i<n;i++){//Plotar os pontos
		        	valorx=achar_x(i);
		        series.appendData(new DataPoint(valorx,user1.distancia_array[MainActivity.usuario_numero].get(i)), true, 3600);
		       
		        }
		        graph.addSeries(series);
	        	
	        break;
	        
	        }
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

	private Double achar_x(int i) {//Achar o valor de x conforme a diferença de datas
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	     String dateInString = user1.Datainicial;
	     dateInString=dateInString.trim();
	     java.util.Date date = null;
	     java.util.Date date1 = null;
	      try {
			date = formatter.parse(dateInString);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		     String dateInString1 = user1.date_array[MainActivity.usuario_numero].get(i);
		      try {
				 date1 = formatter1.parse(dateInString1);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		      double seconds = (-date.getTime()+date1.getTime())/1000;
	         
		      switch(user1.pot_data){
		      case 1:
		      seconds=seconds/60;
		      break;
		      case 2:
		      seconds=seconds/3600;
		      break;
		      case 3:
		      seconds=seconds/86400;
		      break;
		      }
		return (double) seconds;
	}





	private String nome_do_grafico() {//Definir o nome do gráfico conforme qual_gráfico e datainicial
		int n = user1.date_array[MainActivity.usuario_numero].size();
		String Data= user1.date_array[MainActivity.usuario_numero].get(n-1);
		String nome=obter_nome();
		switch(user1.qual_grafico){
		case 0:
		nome=nome+" kcal ";
		break;
		case 1:
		nome=nome+" potência ";
		break;
		case 2:
		nome=nome+" velocidade ";
		break;
		case 3:
		nome=nome+" Distância ";
		break;	
		}
		switch(user1.pot_data){
		case 0:
		nome=nome+user1.Datainicial.substring(0,10);//"dd/MM/yyyy HH:mm:ss"
		  graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
	            public String formatLabel(double value, boolean isValueX) {
	                if (isValueX) {
	                    // show normal x values
	                    return super.formatLabel(value, isValueX)+ "seg";
	                } else {
	                    // show currency for y values
	                    return super.formatLabel(value, isValueX) ;
	                }
	            }
	        });
		break;
		case 1:
		nome=nome+user1.date_array[MainActivity.usuario_numero].get(n-1).substring(0,10);	
		  graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
	            public String formatLabel(double value, boolean isValueX) {
	                if (isValueX) {
	                    // show normal x values
	                    return super.formatLabel(value, isValueX)+ "min";
	                } else {
	                    // show currency for y values
	                    return super.formatLabel(value, isValueX) ;
	                }
	            }
	        });
		break;
		case 2:
		nome=nome+user1.date_array[MainActivity.usuario_numero].get(n-1).substring(0,10);	
		 graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
	            public String formatLabel(double value, boolean isValueX) {
	                if (isValueX) {
	                    // show normal x values
	                    return super.formatLabel(value, isValueX)+ "h";
	                } else {
	                    // show currency for y values
	                    return super.formatLabel(value, isValueX) ;
	                }
	            }
	        });
		break;
		case 3:
		nome=nome+user1.date_array[MainActivity.usuario_numero].get(n-1).substring(0,10);	
		 graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
	            public String formatLabel(double value, boolean isValueX) {
	                if (isValueX) {
	                    // show normal x values
	                    return super.formatLabel(value, isValueX)+ "Dia";
	                } else {
	                    // show currency for y values
	                    return super.formatLabel(value, isValueX) ;
	                }
	            }
	        });
		break;
		}
	
		return nome;
	}


	private String obter_nome() {//Obter o nome do usuário que está usando o gráfico no momento
		String nome="";
		switch(MainActivity.usuario_numero){
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
		return nome;
	}





	public void onBackPressed() {//Quando for apertado o botão para voltar
		super.onBackPressed();
		Intent trocando=new
        Intent(grafico.this,user1.class);//Volta para a tela do usuário
        grafico.this.startActivity(trocando);
        grafico.this.finish();
        
       
	}
}
