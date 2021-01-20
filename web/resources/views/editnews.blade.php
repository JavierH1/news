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
                    <a class="nav-link active" aria-current="page" href="http://127.0.0.1:8000/viewnews">Listado de noticias</a>
                </li>
            </ul>
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
    <title> Edit news </title>
    <meta name="description" content="register news">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
<div class="container">

    <div class="col-sm-6">

        <p class="h1 text-center">Actualizar Noticias</p>

    <!-- form start -->
        <form action="/edit" method="POST">

    @csrf
    <input type="hidden" name="id" value = "{{$editNews['id']}}"> <br> <br>
            <div class="form-group">
                <label for="title" class="col-sm-4 col-form-label">Título de la noticia</label>
                <div> <input type="text" class="form-control" name="title" value = "{{$editNews['title']}}"> </div>
            </div>

            <div class="form-group">
                <label for="author" class="col-sm-4 col-form-label">Autor</label>
                <div> <input type="text" class="form-control" name="author" value = "{{$editNews['author']}}"> </div>
            </div>

            <div class="form-group">
                <label for="source" class="col-sm-4 col-form-label">Fuente</label>
                <div> <input type="text" class="form-control" name="source" value = "{{$editNews['source']}}"> </div>
            </div>

            <div class="form-group">
                <label hidden for="url" class="col-sm-4 col-form-label">URL de la noticia</label>
                <div> <input type="hidden" class="form-control" name="url" value = "{{$editNews['url']}}"> </div>
            </div>

            <div class="form-group">
                <label for="url_image" class="col-sm-4 col-form-label">URL de la imagen</label>
                <div> <input type="text" class="form-control" name="url_image" value = "{{$editNews['url_image']}}"> </div>
            </div>

            <div class="form-group">
                <label for="description" class="col-sm-4 col-form-label">Descripcion de la noticia</label>
                <div> <input type="text" class="form-control" name="description" value = "{{$editNews['description']}}"> </div>
            </div>

            <div class="form-group ">
                <label for="content" class="col-sm-6 col-form-label">Contenido de la noticia</label>
                <div>
                    <textarea id ="content" class="form-control"name="content" rows="6" cols="50">{{$editNews->content}}
                    </textarea>
                </div>
            </div>
                <small id="inputContent" class="form-text text-muted">Ingrese contenido</small>
                <input type="hidden" name="date" value = "{{$editNews['published_at']}}">
            <br>
            <div>
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <div class="col-sm-10">
                    <button type="submit" class="btn btn-success">Registrar</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</div>

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
    <!-- Copyright -->
</footer>
</html>
