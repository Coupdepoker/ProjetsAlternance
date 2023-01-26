<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use Session;
use Illuminate\Support\Facades\Config;
use DateTime;

class HistoriqueController extends Controller
{
    public function gotome(){
        $tmp = Session::get('email_id');
        $results = DB::select('select * from commands where email = ? and payer = ?', [$tmp[0],1]);
        return view("welcome", ["results" => $results]);
    }
}
