<?php

use App\Http\Controllers\NewsController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

// News Routes
Route::resource('news', NewsController::class);

//Route of the news controller to search in api by title
Route::get('searchtitle/{title}', [NewsController::class, 'searchtitle']);

//Route of the news controller to search in api by title
Route::get('searchcontent/{content}', [NewsController::class, 'searchcontent']);

