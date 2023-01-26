<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use Session;
use Illuminate\Support\Facades\Config;
use DateTime;

class CommandeController extends Controller
{
    public function gotome(){
        //$mycommand = DB::select('select * from commands where email = ? and payer = ?',[Session::get('email_id'),0]);
        $tmp = Session::get('email_id');
        $results = DB::select('select * from commands where email = ? and payer = ?', [$tmp[0],0]);
        $text = DB::table('teas')->get();
        //dd($results);
        return view("commande", [ "text" => $text, "results" => $results]);
    }

    public function ajouter(Request $request){
        $validated = $request -> validate([
            'Nom' => 'required',
            'Sucre' => 'required|numeric',
            'Poppings' => 'required|numeric',
        ]);
        $tabmail = Session::get('email_id');
        $date   = new DateTime(); 
        $result = $date->format('Y-m-d-H-i-s');
        DB::insert('insert into commands (name,email,poppings,sucre,date,payer) values (?, ?, ?, ?, ?, ?)', [
            $validated['Nom'], $tabmail[0],$validated['Poppings'],$validated['Sucre'],$result,0]);
        
        return $this->gotome();
    }

    public function payer(){
        $tmp = Session::get('email_id');
        DB::insert('UPDATE commands SET payer = ? WHERE email = ?', [1 ,$tmp[0]]);
        return $this->gotome();
    }

    public function removeAll(){
        $tmp = Session::get('email_id');
        DB::table('commands')->where([['email', $tmp[0]],['payer',0]])->delete();
        return $this->gotome();
    }
}
