<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;

class AdminController extends Controller
{
    public function gotome(){
        $text = DB::table('teas')->get();
        return view("admin", [ "text" => $text ]);
    }

    public function addrow(Request $request){
        $validated = $request -> validate([
            'Nom' => 'required',
            'Image' => 'required',
        ]);

        $path = $request->file('Image')->store('public');
        DB::insert('insert into teas (name,photo) values (?, ?)', [
            $validated['Nom'], $path]);
        
        return $this->gotome();
    }

    public function removerow(Request $request){
        $validated = $request -> validate([
            'Nom' => 'required',
        ]);

        DB::delete('delete from teas where name=?', [
            $validated['Nom']]);
        
        return $this->gotome();
    }

    public function show(){
        $text = DB::table('teas')->get();
        return view("admin", [ "text" => $text ]);
    }
}
