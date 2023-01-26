<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Config;
use Session;
use DB;

class ProfilController extends Controller
{



    public function gotome(){
        return view('profil');
    }

    public function change(Request $request)
    {
        $validated = $request -> validate([
            'Nom' => 'required',
            'Prenom' => 'required',
            'email' => 'required|email:rfc,dns',
            'password' => 'required',
            'passwordConfirm' => 'required|same:password'
        ]);

        $tabmail = Session::get('email_id');
        DB::insert('UPDATE users SET name = ?,prenom = ?,email = ?,password = ? WHERE email = ?', [
            $validated['Nom'], $validated['Prenom'],$validated['email'],$validated['password'],$tabmail[0]]);

        Session::forget('email_id');
        Session::push('email_id', $validated['email']);
        
        return view('commande');
    }

    
}
