package com.example.bluetoothgrafico2;

import java.util.Calendar;
import java.util.Set;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static Button usuarior1;//Variavel auxiliar para o app encontrar o button
	public static Button usuarior2;//Variavel auxiliar para o app encontrar o button
	public static Button usuarior3;//Variavel auxiliar para o app encontrar o button
	public static Button usuarior4;//Variavel auxiliar para o app encontrar o button
	public static Button usuarior5;//Variavel auxiliar para o app encontrar o button
	public static Button usuarior6;//Variavel auxiliar para o app encontrar o button
	public static Button usuarior7;//Variavel auxiliar para o app encontrar o button
	public static int ENABLE_BLUETOOTH = 1;//Valores padr�o para ativar o BLUETOOTH
	public static int SELECT_PAIRED_DEVICE = 2;//Valores padr�o para ativar o BLUETOOTH
	public static int SELECT_DISCOVERED_DEVICE = 3;//Valores padr�o para ativar o BLUETOOTH

	    static TextView statusMessage;//TextView em que estado est� o BLUETOOTH
	    static TextView textSpace;//TextView VALOR
	    ConnectionThread connect;//Vari�vel para conectar ao BLUETOOTH
	    public static int usuario_numero=0;//N�mero de qual usu�rio est� operando
	    public static final String SHARED_PREFS = "sharedPrefs";//Contante para guardar uma v�riavel
	    public static final String nome1="text1";//Variavel auxiliar para guardar uma valor na memoria
	    public static final String nome2="text2";//Variavel auxiliar para guardar uma valor na memoria
	    public static final String nome3="text3";//Variavel auxiliar para guardar uma valor na memoria
	    public static final String nome4="text4";//Variavel auxiliar para guardar uma valor na memoria
	    public static final String nome5="text5";//Variavel auxiliar para guardar uma valor na memoria
	    public static final String nome6="text6";//Variavel auxiliar para guardar uma valor na memoria
	    public static final String nome7="text7";//Variavel auxiliar para guardar uma valor na memoria
	    private String text;//Variavel auxiliar para guardar uma valor na memoria
	    private String text1;//Variavel auxiliar para guardar uma valor na memoria
	    private String text2;//Variavel auxiliar para guardar uma valor na memoria
	    private String text3;//Variavel auxiliar para guardar uma valor na memoria
	    private String text4;//Variavel auxiliar para guardar uma valor na memoria
	    private String text5;//Variavel auxiliar para guardar uma valor na memoria
	    private String text6;//Variavel auxiliar para guardar uma valor na memoria
	    private String text7;//Variavel auxiliar para guardar uma valor na memoria
	    public  String loadData(int i) {//Carregar o valor dos usu�rios que est�o na mem�ria
	      SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
	    switch(i){
	    case 0:
	     text= sharedPreferences.getString("text1", "Usu�rio1");
	    break;
	    case 1:
		text= sharedPreferences.getString("text2", "Usu�rio2");
		break;
	    case 2:
		text= sharedPreferences.getString("text3", "Usu�rio3");
		break;
	    case 3:
		text= sharedPreferences.getString("text4", "Usu�rio4");
		break;
	    case 4:
		text= sharedPreferences.getString("text5", "Usu�rio5");
		break;
	    case 5:
		text= sharedPreferences.getString("text6", "Usu�rio6");
		break;
	    case 6:
		text= sharedPreferences.getString("text7", "Usu�rio7");
		break;
	    }
		return text;

	    }
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {//Ao come�ar o Layout
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        
	        
	        
	        statusMessage = (TextView) findViewById(R.id.statusMessage);//Detectar o TextView statusMessage
	        textSpace = (TextView) findViewById(R.id.textSpace);//Detectar o TextView Valor
	      
	        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();//Ve em qual estado est� o BLUETOOTH
	        if (btAdapter == null) {//N�o funciona
	            statusMessage.setText("Que pena! Hardware Bluetooth n�o esta funcionando ");
	        } else {//Funciona
	            statusMessage.setText("�timo! Hardware Bluetooth est� funcionando ");
	            if(!btAdapter.isEnabled()) {//Requerindo permiss�o para o BLUETOOTH
	                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	                startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);
	                statusMessage.setText("Solicitando ativa��o do Bluetooth...");

	            } else {//Caso ativado
	                statusMessage.setText("Bluetooth j� ativado :)");
	            }
	        }
	        
	        usuarior1 = (Button)findViewById(R.id.button1);//Quando Clicar no primeiro usu�rio
	        usuarior2 = (Button)findViewById(R.id.button2);//Quando Clicar no primeiro usu�rio
	        usuarior3 = (Button)findViewById(R.id.button3);//Quando Clicar no primeiro usu�rio
	        usuarior4 = (Button)findViewById(R.id.button4);//Quando Clicar no primeiro usu�rio
	        usuarior5 = (Button)findViewById(R.id.button5);//Quando Clicar no primeiro usu�rio
	        usuarior6 = (Button)findViewById(R.id.button6);//Quando Clicar no primeiro usu�rio
	        usuarior7 = (Button)findViewById(R.id.button7);//Quando Clicar no primeiro usu�rio
	        for(int i=0;i<7;i++){//Carregar os valores do nomes do usu�rios
	        	 switch(i){
	     	    case 0:
	     	    	usuarior1.setText(loadData(i));
	     	    break;
	     	    case 1:
	     	    	usuarior2.setText(loadData(i));
	     		    break;
	     	    case 2:
	     	    	usuarior3.setText(loadData(i));
	     		    break;
	     	    case 3:
	     	    	usuarior4.setText(loadData(i));
	     		    break;
	     	    case 4:
	     	    	usuarior5.setText(loadData(i));
	     		    break;
	     	    case 5:
	     	    	usuarior6.setText(loadData(i));
	     		    break;
	     	    case 6:
	     	    	usuarior7.setText(loadData(i));
	     		    break;
	     	    }
	        
	        
	        }
	        usuarior1.setOnClickListener(new View.OnClickListener() {//Caso seja escolhido o primeiro usu�rio
	            public void onClick(View arg0){
	            	Intent trocando=new//troca de Layout
	            	Intent(MainActivity.this,user1.class);
	            	usuario_numero=0;//Usuario 1
	            	MainActivity.this.startActivity(trocando);
	            	MainActivity.this.finish();
	            }		
	            });
	        
	        usuarior2.setOnClickListener(new View.OnClickListener() {//Caso seja escolhido o segundo usu�rio
	            public void onClick(View arg0){
	            	Intent trocando=new//troca de Layout
	            	Intent(MainActivity.this,user1.class);
	            	usuario_numero=1;//Usuario 2
	            	MainActivity.this.startActivity(trocando);
	            	MainActivity.this.finish();
	            }		
	            });
	        
	        usuarior3.setOnClickListener(new View.OnClickListener() {//Caso seja escolhido o terceiro usu�rio
	            public void onClick(View arg0){
	            	Intent trocando=new//troca de Layout
	            	Intent(MainActivity.this,user1.class);
	            	usuario_numero=2;//Usuario 3
	            	MainActivity.this.startActivity(trocando);
	            	MainActivity.this.finish();
	            }		
	            });
	        
	        
	        usuarior4.setOnClickListener(new View.OnClickListener() {//Caso seja escolhido o quarto usu�rio
	            public void onClick(View arg0){
	            	Intent trocando=new//troca de Layout
	            	Intent(MainActivity.this,user1.class);
	            	usuario_numero=3;//Usuario 4
	            	MainActivity.this.startActivity(trocando);
	            	MainActivity.this.finish();
	            }		
	            });
	        
	        
	        usuarior5.setOnClickListener(new View.OnClickListener() {//Caso seja escolhido o quinto usu�rio
	            public void onClick(View arg0){
	            	Intent trocando=new//troca de Layout
	            	Intent(MainActivity.this,user1.class);
	            	usuario_numero=4;//Usuario 5
	            	MainActivity.this.startActivity(trocando);
	            	MainActivity.this.finish();
	            }		
	            });
	        
	        usuarior6.setOnClickListener(new View.OnClickListener() {//Caso seja escolhido o sexto usu�rio
	            public void onClick(View arg0){
	            	Intent trocando=new//troca de Layout
	            	Intent(MainActivity.this,user1.class);
	            	usuario_numero=5;//Usuario 6
	            	MainActivity.this.startActivity(trocando);
	            	MainActivity.this.finish();
	            }		
	            });
	        usuarior7.setOnClickListener(new View.OnClickListener() {//Caso seja escolhido o s�timo usu�rio
	            public void onClick(View arg0){
	            	Intent trocando=new//troca de Layout
	            	Intent(MainActivity.this,user1.class);
	            	usuario_numero=6;//Usuario 7
	            	MainActivity.this.startActivity(trocando);
	            	MainActivity.this.finish();
	            }		
	            });
	    }


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();

	        //noinspection SimplifiableIfStatement
	        if (id == R.id.action_settings) {
	            return true;
	        }

	        return super.onOptionsItemSelected(item);
	    }

	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	//Caso haja algum sinal do BLUETOOTH
	        if(requestCode == ENABLE_BLUETOOTH) {//O BLUETOOTH est� ativado?
	            if(resultCode == RESULT_OK) {
	                statusMessage.setText("Bluetooth ativado ");
	            }
	            else {
	                statusMessage.setText("Bluetooth n�o ativado ");
	            }
	        }
	        else if(requestCode == SELECT_PAIRED_DEVICE || requestCode == SELECT_DISCOVERED_DEVICE) {//C�digo para achar o dispositivo
	            if(resultCode == RESULT_OK) {//Se existir um dispositivo conectado
	                statusMessage.setText("Voc� selecionou " + data.getStringExtra("btDevName") + "\n"
	                                        + data.getStringExtra("btDevAddress"));//Qual dispositivo est� sendo usado

	                connect = new ConnectionThread(data.getStringExtra("btDevAddress"));
	                connect.start();//Come�a a conex�o
	            }
	            else {//Caso n�o existir um dispositivo conectado
	                statusMessage.setText("Nenhum dispositivo selecionado ");
	            }
	        }
	    }
	    public void searchPairedDevices(View view) {//Selecionar o Dispositivo j� descoberto

	        Intent searchPairedDevicesIntent = new Intent(this, PairedDevices.class);
	        startActivityForResult(searchPairedDevicesIntent, SELECT_PAIRED_DEVICE);
	    }

	    public void discoverDevices(View view) {//Descobrir dispositivos Novos

	        Intent searchPairedDevicesIntent = new Intent(this, DiscoveredDevices.class);
	        startActivityForResult(searchPairedDevicesIntent, SELECT_DISCOVERED_DEVICE);
	    }
	   
	    public static Handler handler = new Handler() {//Caso haja um Handle enviado para o MainActivity
	        @Override
	        public void handleMessage(Message msg) {

	            Bundle bundle = msg.getData();
	            byte[] data = bundle.getByteArray("data");    
	            
	            textSpace.setText(new String(data));//TextView Valor  assume o valor recebido na mensagem
	        }
	    };

}


