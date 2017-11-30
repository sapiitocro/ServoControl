package com.tecsup.salazar.servocontrol;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //1)
    Button IdAbrir, IdCerrar,IdDesconectar,abrirmenique,abririndice,abrirmedio,abrirpulgar,abriranular,cerrarmenique,cerraranular,cerrarmedio,cerrarindice,cerrarpulgar;
    SeekBar IdMeñique,IdAnular,IdMedio,IdIndice,IdPulgar;
    TextView IdtextMeñique,IdtextAnular,IdtextMedio,IdtextIndice,IdtextPulgar;
    //-------------------------------------------
    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private ConnectedThread MyConexionBT;
    // Identificador unico de servicio - SPP UUID
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String para la direccion MAC
    private static String address = null;
    //-------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //2)
        //Enlaza los controles con sus respectivas vistas
        IdAbrir = (Button) findViewById(R.id.IdAbrir);
        IdCerrar = (Button) findViewById(R.id.IdCerrar);
        IdDesconectar = (Button) findViewById(R.id.IdDesconectar);
        IdMeñique= (SeekBar)findViewById(R.id.IdMeñique);
        IdtextMeñique=(TextView)findViewById(R.id.IdtextMeñique);
        IdAnular= (SeekBar)findViewById(R.id.IdAnular);
        IdtextAnular=(TextView)findViewById(R.id.IdtextAnular);
        IdMedio= (SeekBar)findViewById(R.id.IdMedio);
        IdtextMedio=(TextView)findViewById(R.id.IdtextMedio);
        IdIndice= (SeekBar)findViewById(R.id.IdIndice);
        IdtextIndice=(TextView)findViewById(R.id.IdtextIndice);
        IdPulgar= (SeekBar)findViewById(R.id.IdPulgar);
        IdtextPulgar=(TextView)findViewById(R.id.IdtextPulgar);
        IdtextMeñique.setText("Valor: "+ IdMeñique.getProgress() + " / " + IdMeñique.getMax());
        IdtextAnular.setText("Valor: "+ IdAnular.getProgress() + " / " + IdAnular.getMax());
        IdtextMedio.setText("Valor: "+ IdMedio.getProgress() + " / " + IdMedio.getMax());
        IdtextIndice.setText("Valor: "+ IdIndice.getProgress() + " / " + IdIndice.getMax());
        IdtextPulgar.setText("Valor: "+ IdPulgar.getProgress() + " / " + IdPulgar.getMax());
        abrirmenique=(Button)findViewById(R.id.abrirmenique);
        abriranular=(Button)findViewById(R.id.abriranular);
        abrirmedio=(Button)findViewById(R.id.abrirmedio);
        abririndice=(Button)findViewById(R.id.abririndice);
        abrirpulgar=(Button)findViewById(R.id.abrirpulgar);
        cerrarmenique=(Button)findViewById(R.id.cerrarmenique);
        cerraranular=(Button)findViewById(R.id.cerraranular);
        cerrarmedio=(Button)findViewById(R.id.cerrarmedio);
        cerrarindice=(Button)findViewById(R.id.cerrarindice);
        cerrarpulgar=(Button)findViewById(R.id.cerrarpulgar);


        btAdapter = BluetoothAdapter.getDefaultAdapter(); // get Bluetooth adapter
        VerificarEstadoBT();

        // Configuracion onClick listeners para los botones
        // para indicar que se realizara cuando se detecte
        // el evento de Click
        abrirmenique.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                MyConexionBT.write("a");
                MyConexionBT.write("0");
            }
        });

        abriranular.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyConexionBT.write("b");
                MyConexionBT.write("0");
            }
        });
        abrirmedio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyConexionBT.write("c");
                MyConexionBT.write("0");
            }
        });
        abririndice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyConexionBT.write("d");
                MyConexionBT.write("0");
            }
        });
        abrirpulgar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MyConexionBT.write("e");
                MyConexionBT.write("0");
            }
        });
        IdAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyConexionBT.write("f");
                MyConexionBT.write("0");

            }
        });
        cerrarmenique.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                MyConexionBT.write("a");
                MyConexionBT.write("180");
            }
        });
        cerraranular.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                MyConexionBT.write("b");
                MyConexionBT.write("180");
            }
        });
        cerrarmedio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                MyConexionBT.write("c");
                MyConexionBT.write("180");
            }
        });
        cerrarindice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                MyConexionBT.write("d");
                MyConexionBT.write("180");
            }
        });
        cerrarpulgar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                MyConexionBT.write("e");
                MyConexionBT.write("180");
            }
        });
        IdCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyConexionBT.write("f");
                MyConexionBT.write("180");
            }
        });
        IdMeñique.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress_value=i;
                IdtextMeñique.setText("Valor: "+ i + " / " + IdMeñique.getMax());
                MyConexionBT.write("a");
                MyConexionBT.write(String.valueOf(i));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        IdAnular.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress_value=i;
                IdtextAnular.setText("Valor: "+ i + " / " + IdAnular.getMax());
                MyConexionBT.write("b");
                MyConexionBT.write(String.valueOf(i));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        IdMedio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress_value=i;
                IdtextMedio.setText("Valor: "+ i + " / " + IdMedio.getMax());
                MyConexionBT.write("c");
                MyConexionBT.write(String.valueOf(i));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        IdIndice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress_value=i;
                IdtextIndice.setText("Valor: "+ i + " / " + IdIndice.getMax());
                MyConexionBT.write("d");
                MyConexionBT.write(String.valueOf(i));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        IdPulgar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int progress_value=i;
                IdtextPulgar.setText("Valor: "+ i + " / " + IdPulgar.getMax());
                MyConexionBT.write("e");
                MyConexionBT.write(String.valueOf(i));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        IdDesconectar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (btSocket!=null)
                {
                    try {btSocket.close();}
                    catch (IOException e)
                    { Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();;}
                }
                finish();
            }
        });
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        //crea un conexion de salida segura para el dispositivo
        //usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //Consigue la direccion MAC desde DeviceListActivity via intent
        Intent intent = getIntent();
        //Consigue la direccion MAC desde DeviceListActivity via EXTRA
        address = intent.getStringExtra(DispositivoBTActivity.EXTRA_DEVICE_ADDRESS);//<-<- PARTE A MODIFICAR >->->
        //Setea la direccion MAC
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try
        {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
        }
        // Establece la conexión con el socket Bluetooth.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {}
        }
        MyConexionBT = new ConnectedThread(btSocket);
        MyConexionBT.start();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        { // Cuando se sale de la aplicación esta parte permite
            // que no se deje abierto el socket
            btSocket.close();
        } catch (IOException e2) {}
    }

    //Comprueba que el dispositivo Bluetooth Bluetooth está disponible y solicita que se active si está desactivado
    private void VerificarEstadoBT() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //Crea la clase que permite crear el evento de conexion
    private class ConnectedThread extends Thread
    {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try
            {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[256];
            int bytes;

            // Se mantiene en modo escucha para determinar el ingreso de datos
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    // Envia los datos obtenidos hacia el evento via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //Envio de trama
        public void write(String input)
        {
            try {
                mmOutStream.write(input.getBytes());
            }
            catch (IOException e)
            {
                //si no es posible enviar datos se cierra la conexión
                Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}