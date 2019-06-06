package com.example.bluetoothgrafico2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;

public class ConnectionThread extends Thread{
	 
    BluetoothSocket btSocket = null;
    BluetoothServerSocket btServerSocket = null;
    InputStream input = null;
    OutputStream output = null;
    String btDevAddress = null;
    String myUUID = "00001101-0000-1000-8000-00805F9B34FB";
    boolean server;
    boolean running = false;
    boolean isConnected = false;
 
    /*  Este construtor prepara o dispositivo para atuar como servidor.
     */
    public ConnectionThread() {
 
        this.server = true;
    }
 
    /*  Este construtor prepara o dispositivo para atuar como cliente.
        Tem como argumento uma string contendo o endere�o MAC do dispositivo
    Bluetooth para o qual deve ser solicitada uma conex�o.
     */
    public ConnectionThread(String btDevAddress) {
 
        this.server = false;
        this.btDevAddress = btDevAddress;
    }
 
    /*  O m�todo run() contem as instru��es que ser�o efetivamente realizadas
    em uma nova thread.
     */
    public void run() {
 
        /*  Anuncia que a thread est� sendo executada.
            Pega uma refer�ncia para o adaptador Bluetooth padr�o.
         */
        this.running = true;
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
 
        /*  Determina que a��es executar dependendo se a thread est� configurada
        para atuar como servidor ou cliente.
         */
        if(this.server) {
 
            /*  Servidor.
             */
            try {
 
                /*  Cria um socket de servidor Bluetooth.
                    O socket servidor ser� usado apenas para iniciar a conex�o.
                    Permanece em estado de espera at� que algum cliente
                estabele�a uma conex�o.
                 */
                btServerSocket = btAdapter.listenUsingRfcommWithServiceRecord("Super Counter", UUID.fromString(myUUID));
                btSocket = btServerSocket.accept();
 
                /*  Se a conex�o foi estabelecida corretamente, o socket
                servidor pode ser liberado.
                 */
                if(btSocket != null) {
 
                    btServerSocket.close();
                }
 
            } catch (IOException e) {
 
                /*  Caso ocorra alguma exce��o, exibe o stack trace para debug.
                    Envia um c�digo para a Activity principal, informando que
                a conex�o falhou.
                 */
                e.printStackTrace();
                //toMainActivity("---N".getBytes());
            }
 
 
        } else {
 
            /*  Cliente.
             */
            try {
 
                /*  Obtem uma representa��o do dispositivo Bluetooth com
                endere�o btDevAddress.
                    Cria um socket Bluetooth.
                 */
                BluetoothDevice btDevice = btAdapter.getRemoteDevice(btDevAddress);
                btSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString(myUUID));
 
                /*  Envia ao sistema um comando para cancelar qualquer processo
                de descoberta em execu��o.
                 */
                btAdapter.cancelDiscovery();
 
                /*  Solicita uma conex�o ao dispositivo cujo endere�o �
                btDevAddress.
                    Permanece em estado de espera at� que a conex�o seja
                estabelecida.
                 */
                if (btSocket != null) {
                    btSocket.connect();
                }
 
            } catch (IOException e) {
 
                /*  Caso ocorra alguma exce��o, exibe o stack trace para debug.
                    Envia um c�digo para a Activity principal, informando que
                a conex�o falhou.
                 */
                e.printStackTrace();
               // toMainActivity("---N".getBytes());
            }
 
        }
 
        /*  Pronto, estamos conectados! Agora, s� precisamos gerenciar a conex�o.
            ...
         */
 
        if(btSocket != null) {
 
            /*  Envia um c�digo para a Activity principal informando que a
            a conex�o ocorreu com sucesso.
             */
            this.isConnected = true;
            //toMainActivity("---S".getBytes());
 
            try {
 
                /*  Obtem refer�ncias para os fluxos de entrada e sa�da do
                socket Bluetooth.
                 */
                input = btSocket.getInputStream();
                output = btSocket.getOutputStream();
 
                /*  Permanece em estado de espera at� que uma mensagem seja
                recebida.
                    Armazena a mensagem recebida no buffer.
                    Envia a mensagem recebida para a Activity principal, do
                primeiro ao �ltimo byte lido.
                    Esta thread permanecer� em estado de escuta at� que
                a vari�vel running assuma o valor false.
                 */
                while(running) {
 
                    /*  Cria um byte array para armazenar temporariamente uma
                    mensagem recebida.
                        O inteiro bytes representar� o n�mero de bytes lidos na
                    �ltima transmiss�o recebida.
                        O inteiro bytesRead representa o n�mero total de bytes
                    lidos antes de uma quebra de linha. A quebra de linha
                    representa o fim da mensagem.
                     */
                    byte[] buffer = new byte[1024];
                    int bytes;
                    int bytesRead = -1;
 
                    /*  L� os bytes recebidos e os armazena no buffer at� que
                    uma quebra de linha seja identificada. Nesse ponto, assumimos
                    que a mensagem foi transmitida por completo.
                     */
                    do {
                        bytes = input.read(buffer, bytesRead+1, 1);
                        bytesRead+=bytes;
                    } while(buffer[bytesRead] != '\n');
 
                    /*  A mensagem recebida � enviada para a Activity principal.
                     */
                    toMainActivity(Arrays.copyOfRange(buffer, 0, bytesRead-1));
 
                }
 
            } catch (IOException e) {
 
                /*  Caso ocorra alguma exce��o, exibe o stack trace para debug.
                    Envia um c�digo para a Activity principal, informando que
                a conex�o falhou.
                 */
                e.printStackTrace();
                //toMainActivity("---N".getBytes());
                this.isConnected = false;
            }
        }
 
    }
 
    /*  Utiliza um handler para enviar um byte array � Activity principal.
        O byte array � encapsulado em um Bundle e posteriormente em uma Message
    antes de ser enviado.
     */
    private void toMainActivity(byte[] data) {
 
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putByteArray("data", data);
        message.setData(bundle);
        //MainActivity.handler.sendMessage(message);
        user1.handler.sendMessage(message);
        
        
    }
 
    /*  M�todo utilizado pela Activity principal para transmitir uma mensagem ao
     outro lado da conex�o.
        A mensagem deve ser representada por um byte array.
     */
    public void write(byte[] data) {
 
        if(output != null) {
            try {
 
                /*  Transmite a mensagem.
                 */
                output.write(data);
 
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
 
            /*  Envia � Activity principal um c�digo de erro durante a conex�o.
             */
           // toMainActivity("---N".getBytes());
        }
    }
 
    /*  M�todo utilizado pela Activity principal para encerrar a conex�o
     */
    public void cancel() {
 
        try {
 
            running = false;
            this.isConnected = false;
            btServerSocket.close();
            btSocket.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = false;
        this.isConnected = false;
    }
 
    public boolean isConnected() {
        return this.isConnected;
    }
}