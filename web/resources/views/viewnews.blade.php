@section('content')
<!doctype html>
<html>
<!-- Image and text navbar-->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Laravel News</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="http://127.0.0.1:8000/registernews">Registro de noticias</a>
                </li>
            </ul>
            <!-- <form class="d-flex">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>-->
        </div>
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

    <table class ="table-light table-bordered">
        <tr>
            <td>id</td>
            <td>Título</td>
            <td>Autor</td>
            <td>Fuente</td>
            <td>URL</td>
            <td>Imagen</td>
            <td>Descripcion</td>
            <td>Contenido</td>
            <td>Fecha</td>
            <td>Operación</td>

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


                <a href = "{{"edit/".$news['id']}}" class="btn btn-primary btn-sm active" role=" button"
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
        <div class="text-center p-4" style="background-color: #FAFAFA;">
        © 2021 Copyright:
        <a class="text-dark" href="https://mdbootstrap.com/">UCN - Desarrollo de Soluciones moviles</a>
    </div>
    </div>

    <!-- Copyright -->
</footer>
</html>
