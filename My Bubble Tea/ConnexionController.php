<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use View;
use Illuminate\Support\Facades\Config;
use Session;

class ConnexionController extends Controller
{
    public function login(Request $request)
    {
        $validated = $request -> validate([
            'email' => 'required|email:rfc,dns',
            'password' => 'required'
        ]);
        $results = DB::select('select * from users where email = ? and password = ?', [$validated['email'],$validated['password']]);
        Session::forget('email_id');
        $email_id = $validated['email'];
        Session::push('email_id', $email_id);
        if(empty($results)){
            return view('connect');
        }else{
            if($email_id === 'admin@gmail.com'){
                $text = DB::table('teas')->get();
                return view("admin", [ "text" => $text ]);
            }else{
                $tmp = Session::get('email_id');
                $results = DB::select('select * from commands where email = ? and payer = ?', [$tmp[0],1]);
                return view("welcome", ["results" => $results]);
            }
        }
        ;
        
    }

    public function authentifier(Request $request)
    {
        $request ->validate([
        'nom'=> 'required',
        'prenom'=>'required',
        'email' =>'required|email',
        'password'=>'required'
        ]);
       if (auth()->attempt($request->only('email','password')))
       {
            return redirect()->route('dashboard');
       }
       return redirect()->back()->withErrors('identifiants non valide');
    }

    public function logout()
    {
        //
    }

    public function create()
    {
        return view('welcome'); // next:- page name created with next.blade.php 
    }

    function con() {
        return view('connect');;
    }
}
