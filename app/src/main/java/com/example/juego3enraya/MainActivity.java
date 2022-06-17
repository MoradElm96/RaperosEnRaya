package com.example.juego3enraya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;


public class MainActivity extends AppCompatActivity {



    TextView txtResultado;
    Integer[] botones;

    //tablero para ir guardando lo que colocamos
    int [] tablero = new int[]{
        0,0,0,
        0,0,0,
        0,0,0};


    int estado = 0; // 0 si no hay ningun ganador,1 si hemos ganado, -1 si la cpu gana, 2 si es empate
    int fichasPuestas=0;

    //para ganadores
    int turno =1; //1 o -1, si es 1 ganamos nosotros
    int [] posicionGanadora = new int[]{-1,-1,-1};


    Button jugar;

    MediaPlayer mpE,mpC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        txtResultado = (TextView) findViewById(R.id.txtResultado);
        txtResultado.setVisibility(View.INVISIBLE);



        //poner en orden los botones
        botones =  new Integer[]{

                R.id.btn1,R.id.btn2,R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9
        };

        jugar = (Button) findViewById(R.id.btnJugar);


        mpE = MediaPlayer.create(this, R.raw.eminem);
        mpC = MediaPlayer.create(this, R.raw.icecube);



    }

    public void JugarDeNuevo(View v) {


           Toast.makeText(this,"Reseteando partida", Toast.LENGTH_LONG).show();

           Intent intent = getIntent();
           finish();
           startActivity(intent);




    }


    public  void ponerFicha(View vista){



        if(estado==0) {

            turno=1;

            //saber que boton a pulsado el usuario
            int numeroBtn = Arrays.asList(botones).indexOf(vista.getId()); // devuelve la posicion que es cuando se pulsa el boton

            //comprobar que no pongamos ficha donde ya esta

            if(tablero[numeroBtn] == 0) {

                vista.setBackgroundResource(R.drawable.icecubefunkop);
                tablero[numeroBtn] = 1; //porque empezamos nosotros
                fichasPuestas += 1;
                estado = comprobarEstado();
                terminarPartida();

                if (estado == 0) {
                    turno=-1;
                    maquinaCpu();
                    fichasPuestas += 1;
                    estado = comprobarEstado();
                    terminarPartida();

                }
            }




        }




    }



    public  int comprobarEstado(){

       int nuevoEstado = 0; //seguimos jugandd

        //primera condicion que 123 sea raya
        if(Math.abs(tablero[0]+tablero[1]+tablero[2])==3){ //3 porque ponemos un 1, se pasa a  valor absoluto

            posicionGanadora = new int[]{0,1,2};
            nuevoEstado =1*turno;



        }else if(Math.abs(tablero[3]+tablero[4]+tablero[5])==3){
            posicionGanadora = new int[]{3,4,5};
            nuevoEstado =1*turno;

        }else if(Math.abs(tablero[6]+tablero[7]+tablero[8])==3){
            posicionGanadora = new int[]{6,7,8};
            nuevoEstado =1*turno;
//ahora las columnas
        }else if(Math.abs(tablero[0]+tablero[3]+tablero[6])==3){
            posicionGanadora = new int[]{0,3,6};
            nuevoEstado =1*turno;

        }else if(Math.abs(tablero[1]+tablero[4]+tablero[7])==3){
            posicionGanadora = new int[]{1,4,7};
            nuevoEstado =1*turno;

        }else if(Math.abs(tablero[2]+tablero[5]+tablero[8])==3){
            posicionGanadora = new int[]{2,5,8};
            nuevoEstado =1*turno;
//ahora diagonales
        }else if(Math.abs(tablero[0]+tablero[4]+tablero[8])==3){
            posicionGanadora = new int[]{0,4,8};
            nuevoEstado =1*turno;

        }else if(Math.abs(tablero[2]+tablero[4]+tablero[6])==3){
            posicionGanadora = new int[]{2,4,6};
            nuevoEstado =1*turno;

   //ahora si hemos empatado
        }else if(fichasPuestas == 9){
            nuevoEstado=2;



        }




return  nuevoEstado;
    }



    public  void terminarPartida(){

        int fichaVictoria = R.drawable.icecubeyesssssp;

        if(estado ==1 || estado ==-1){

            if(estado==1){
                txtResultado.setVisibility(View.VISIBLE);
                txtResultado.setText(" Win!! Ice cube for president! ;)");
                mpC.start();
                Toast.makeText(this,"ice cube gana", Toast.LENGTH_LONG).show();
                txtResultado.setTextColor(Color.GREEN);
            }else{

                txtResultado.setVisibility(View.VISIBLE);
                txtResultado.setText("Has tenido suerte -.-");
                mpE.start();
                Toast.makeText(this,"eminem gana", Toast.LENGTH_LONG).show();
                txtResultado.setTextColor(Color.RED);
                fichaVictoria = R.drawable.eminemwiinp;


            }
            for(int i =0; i<posicionGanadora.length; i++){
                Button b = findViewById(botones[posicionGanadora[i]]);
                b.setBackgroundResource(fichaVictoria);

            }



        }else if(estado ==2){
            txtResultado.setVisibility(View.VISIBLE);
            txtResultado.setTextColor(Color.BLUE);
            txtResultado.setText("¿como has podido empatar?");
            Toast.makeText(this,"Empate", Toast.LENGTH_LONG).show();

        }
    }









    public void maquinaCpu(){
        Random ran = new Random();
        //crear numero aleatorio entre 0 y 8
        int pos = ran.nextInt(tablero.length);
        //si esta ocupada

        while(tablero[pos]!=0){
            pos = ran.nextInt(tablero.length);
        }

        Button b = (Button) findViewById(botones[pos]);
        b.setBackgroundResource(R.drawable.eminemfunkop);
        tablero [pos] = -1;






    }


}