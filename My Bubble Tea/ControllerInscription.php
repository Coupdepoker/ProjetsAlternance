<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;

class ControllerInscription extends Controller
{
    /**
     * Display register page.
     * 
     * @return \Illuminate\Http\Response
     */
    public function show(Request $request)
    {
        $validated = $request -> validate([
            'Nom' => 'required',
            'Prenom' => 'required',
            'email' => 'required|email:rfc,dns',
            'password' => 'required',
            'passwordConfirm' => 'required|same:password'
        ]);

        DB::insert('insert into users (name,prenom,email,password) values (?, ?, ?, ?)', [
            $validated['Nom'], $validated['Prenom'],$validated['email'],$validated['password']]);
        
        return view('connect');
    }

}