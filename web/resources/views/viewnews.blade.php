@section('content')
<!doctype html>
<html>
<!-- Image and text navbar-->
<nav class="navbar navbar-light" style="background-color: #e3f2fd;"><!-- prueba de color-->
    <div class="container-fluid">
    <span class="navbar-brand mb-0 h1">
            Universidad Católica del Norte
        </span>
    </div>
</nav>

<br/>

<head>
    <div class="container">
        <!-- Content here -->
        <br>
    </div>
    <meta charset="utf-8">
    <title>View of news</title>
    <meta name="description" content="views news">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
<div class="container">

    <div class="col-sm-6">

        <p class="h1 text-center">Listado de noticias</p>
    </div>

    <table border ="1">
        <tr>
            <td>id</td>
            <td>Title</td>
            <td>Author</td>
            <td>Source</td>
            <td>URL</td>
            <td>image URL</td>
            <td>Description</td>
            <td>Content</td>
            <td>Published_At</td>
            <td>Operation</td>

        </tr>
        @foreach($listNews as $news)

            <tr>
                <td>{{$news['id']}}</td>
                <td>{{$news['title']}}</td>
                <td>{{$news['author']}}</td>
                <td>{{$news['source']}}</td>
                <td>{{$news['url']}}</td>
                <td>{{$news['url_image']}}</td>
                <td>{{$news['description']}}</td>
                <td>{{$news['content']}}</td>
                <td>{{$news['published_at']}}</td>
                <td>
                <a href = "{{"edit/".$news['id']}}" class="btn btn-success btn-sm active" role=" button"
                aria-pressed="=true">Editar</a>
                <a href = "{{"delete/".$news['id']}}" class="btn btn-danger btn-sm active"
                   onclick="return confirm('¿Desea borrar esta noticia seleccionada?')"
                   role=" button"
                   aria-pressed="=true">Borrar</a>
                </td>
            </tr>
        @endforeach
    </table>
    <div class="text-xs-center">
        <ul class="pagination justify-content-center">
            {{$listNews->onEachSide(5)->links()}}
        </ul>
    </div>

</div>

</body>

<br/>
<br/>
<br/>

<!-- footer -->
<footer class="bg-light text-left text-lg-start">
    <!-- Grid container -->
    <div class="container p-4">
        <!--Grid row-->

        <div>
            <h6 class="text-uppercase"> Project The News Api </h6>

            <!--Grid row-->
        </div>
        <!-- Grid container -->

        <!-- Copyright -->
        <div class="text-center p-4" style="background-color: #e3f2fd;">
        © 2021 Copyright:
        <a class="text-dark" href="https://mdbootstrap.com/">UCN - Desarrollo de Soluciones moviles</a>
    </div>
    </div>

    <!-- Copyright -->
</footer>
</html>
