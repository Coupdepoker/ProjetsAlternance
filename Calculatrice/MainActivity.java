package a.b.calculatricerami;


import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.appodeal.ads.Appodeal;
import com.example.calculatricerami.R;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity {

    public boolean beginString = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Appodeal.setBannerViewId(R.id.appodealBannerView);
        Appodeal.initialize(this, "de9e66b9a64813d8782f102600256fa697923c7c4d09a9d0", Appodeal.BANNER, true);
        Appodeal.show(this, Appodeal.BANNER_TOP);

        TextView screen = (TextView) findViewById(R.id.screen);

        Button btnSev=(Button)findViewById(R.id.btnSeven);
        btnSev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("7");
                    beginString = true;
                }else{
                    screen.append("7");
                }
            }
        });

        Button btnEight=(Button)findViewById(R.id.btnEight);
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("8");
                    beginString=true;
                }else{
                    screen.append("8");
                }
            }
        });

        Button btnNine=(Button)findViewById(R.id.btnNine);
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("9");
                    beginString=true;
                }else{
                    screen.append("9");
                }
            }
        });

        Button btnPlus=(Button)findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("+");
                    beginString=true;
                }else{
                    screen.append("+");
                }
            }
        });

        Button btnFour=(Button)findViewById(R.id.btnFour);
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("4");
                    beginString=true;
                }else{
                    screen.append("4");
                }
            }
        });

        Button btnFive=(Button)findViewById(R.id.btnFive);
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("5");
                    beginString=true;
                }else{
                    screen.append("5");
                }
            }
        });

        Button btnSix=(Button)findViewById(R.id.btnSix);
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("6");
                    beginString=true;
                }else{
                    screen.append("6");
                }
            }
        });

        Button btnMinus=(Button)findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("-");
                    beginString=true;
                }else{
                    screen.append("-");
                }
            }
        });

        Button btnThree=(Button)findViewById(R.id.btnThree);
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("3");
                    beginString=true;
                }else{
                    screen.append("3");
                }
            }
        });

        Button btnTwo=(Button)findViewById(R.id.btnTwo);
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("2");
                    beginString=true;
                }else{
                    screen.append("2");
                }
            }
        });

        Button btnOne=(Button)findViewById(R.id.btnOne);
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("1");
                    beginString=true;
                }else{
                    screen.append("1");
                }
            }
        });

        Button btnMul=(Button)findViewById(R.id.btnMult);
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("*");
                    beginString=true;
                }else{
                    screen.append("*");
                }
            }
        });

        Button btnZero=(Button)findViewById(R.id.btnZero);
        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("0");
                    beginString=true;
                }else{
                    screen.append("0");
                }
            }
        });

        Button btnPoint=(Button)findViewById(R.id.btnPoint);
        btnPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText(".");
                    beginString=true;
                }else{
                    screen.append(".");
                }
            }
        });

        Button btnEg = (Button)findViewById(R.id.btnEqual);
        btnEg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = Context.enter(); //
                context.setOptimizationLevel(-1); // this is required[2]
                Scriptable scope = context.initStandardObjects();
                try{
                    String formule = screen.getText().toString();
                    Object result = context.evaluateString(scope, formule, "<cmd>", 1, null);
                    screen.setText(result.toString());
                }catch (Exception e){
                    screen.setText("Error");
                }
                //beginString = false;
            }
        });

        Button btnClear=(Button)findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen.setText("");
                beginString = false;
            }
        });

        Button btnDiv=(Button)findViewById(R.id.btnDiv);
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("/");
                    beginString=true;
                }else{
                    screen.append("/");
                }
            }
        });

        Button btnModulo=(Button)findViewById(R.id.btnModulo);
        btnModulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("%");
                    beginString=true;
                }else{
                    screen.append("%");
                }
            }
        });

        Button btnPG=(Button)findViewById(R.id.btnPG);
        btnPG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("(");
                    beginString=true;
                }else{
                    screen.append("(");
                }
            }
        });

        Button btnPD=(Button)findViewById(R.id.btnPD);
        btnPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText(")");
                    beginString=true;
                }else{
                    screen.append(")");
                }
            }
        });

        Button btnPower=(Button)findViewById(R.id.btnPower);
        btnPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("Math.pow(");
                    beginString=true;
                }else{
                    screen.append("Math.pow(");
                }
            }
        });

        Button btnVirg=(Button)findViewById(R.id.btnVirg);
        btnVirg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText(",");
                    beginString=true;
                }else{
                    screen.append(",");
                }
            }
        });

        Button btnCos=(Button)findViewById(R.id.btnCos);
        btnCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("Math.cos(");
                    beginString=true;
                }else{
                    screen.append("Math.cos(");
                }
            }
        });

        Button btnSin=(Button)findViewById(R.id.btnSin);
        btnSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("Math.sin(");
                    beginString=true;
                }else{
                    screen.append("Math.sin(");
                }
            }
        });

        Button btnPi=(Button)findViewById(R.id.btnPi);
        btnPi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("Math.PI");
                    beginString=true;
                }else{
                    screen.append("Math.PI");
                }
            }
        });

        Button btnExp=(Button)findViewById(R.id.btnExp);
        btnExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("Math.exp(");
                    beginString=true;
                }else{
                    screen.append("Math.exp(");
                }
            }
        });

        Button btnRac=(Button)findViewById(R.id.btnRacine);
        btnRac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("Math.sqrt(");
                    beginString=true;
                }else{
                    screen.append("Math.sqrt(");
                }
            }
        });

        screen.setMovementMethod(new ScrollingMovementMethod());

        Button btnLn=(Button)findViewById(R.id.btnLn);
        btnLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beginString == false){
                    screen.setText("Math.log(");
                    beginString=true;
                }else{
                    screen.append("Math.log(");
                }
            }
        });
    }
}