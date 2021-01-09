<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\NewsController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function (){
    return view('registernews');
});

// Route to the news form
Route::view('registernews', 'registernews');

// Route of the news controller to store
Route::post('registernews', [NewsController::class, 'store']);

// Route of the news controller to show the stored news
Route::get('viewnews', [NewsController::class, 'show']);

// Route of the news controller to edit or update the stored news
Route::get('edit/{id}', [NewsController::class, 'edit']);

//Route of the news  controller to update the stored new
Route::post('edit',[NewsController::class, 'update']);

//Route of the news controller to delete the stored new
Route::get('delete/{id}', [NewsController::class, 'destroy']);
