<?php

namespace App\Http\Controllers;

use App\Models\News;
use http\Env\Response;
use Illuminate\Contracts\Support\Responsable;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use Illuminate\Http\Resources\Json;


class NewsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
         //SELECT * FROM News
        $news = News::all();

        //Return view Api in json
        return response()->json( $news,200);

        //Search in API
        $title = $request->get('search');
        $news = News::title($title)->paginate(2);
        return view('viewnews' ,compact('News'));
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        // The validation of the required
        $this->validate($request, [
            'title' => 'required',
            'author' => 'required',
            'source' => 'required',
            'url' => 'required',
            'description' => 'required',
            'content' => 'required',
            'date' => 'required'
        ]);

        // Creates a News
        $news = new News();

        // Get  the title of the news
        $news->title = $request->input('title');

        // Get the author of the news
        $news->author = $request->input('author');

        // Get the source of the news
        $news->source = $request->input('source');

        // Get the URL of the news
        $news->url = $request->input('url');

        // Get the URL image of the news
        $news->url_image = $request->input('url_image');

        // Get the description of the news
        $news->description = $request->input('description');

        // Get the content of the news
        $news->content = $request->input('content');

        // Get the publish date of the news
        $news->published_at = $request->input('date');

        // Save the news into database
        $news->save();
        $news = News::all();

        return back()->with('message', 'Se ha agregado la noticia correctamente!');
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Responsecontent
     */
    public function show()
    {
        //Show the list of news in the database.
        $data = News::orderBy('published_at', 'DESC') -> paginate(3);
        return view('viewnews', ['listNews' => $data]);
    }

    /**
     * Search in API news by Title
     * @param $title
     * @return \Illuminate\Http\JsonResponse
     */
    public function searchtitle($title)

    {
        //Search by title example:
        //http://127.0.0.1:8000/api/searchtitle/wordtosearch
    $news = News::where('title','like',"%{$title}%")->get();

    return response()->json(['title' => $news]);

    }

    /**
     * Search in API news by word in content
     * @param $content
     * @return \Illuminate\Http\JsonResponse
     */
    public function searchcontent($content)

    {
        //Search by content example:
        //http://127.0.0.1:8000/api/searchcontent/wordtosearch
        $news = News::where('content','like',"%{$content}%")->get();
        return response()->json(['content' => $news]);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $data =  News::find($id);
        return view('editnews', ['editNews' => $data]);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request)
    {
        $data = News::find($request->id);
        $data ->title = $request->input('title');
        $data ->author = $request->input('author');
        $data ->source = $request->input('source');
        $data ->url =$request->input('url');
        $data ->url_image =$request->input('url_image');
        $data ->description = $request->input('description');
        $data ->content = $request->input('content');
        $data ->published_at= $request->input('date');

        $data->save();
        return redirect('viewnews');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $data = News::find($id);
        $data ->delete();
        return redirect('viewnews');

    }
}
