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
    <title>Register News</title>
    <meta name="description" content="register news">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
<div class="container">

    <div class="col-sm-6">

        <p class="h1 text-center">Registro de noticias</p>
        <!-- validation -->
        @if($errors -> any())

            <div class="alert alert-danger">

                <ul>
                    @foreach($errors-> all() as $error)
                        <li>{{ $error }}</li>
                    @endforeach

                </ul>

            </div>

        @endif
        @if(session('message'))
            <div class="alert alert-success">

                <p>{{session('message')}}</p>

            </div>
    @endif

    <!-- form start -->
        <form action="registernews" method="POST">

            @csrf

            <div class="form-group">
                <label for="title" class="col-sm-4 col-form-label">Título de la noticia</label>
                <div>
                    <input type="text" class="form-control" name="title" placeholder="ingrese el titulo de la noticia"
                           value="{{old('title')}}">
                </div>

            </div>

            <div class="form-group">
                <label for="author" class="col-sm-4 col-form-label">Autor</label>
                <div>
                    <input type="text" class="form-control" name="author" placeholder="Ingrese el autor"
                           value="{{old('author')}}">
                </div>
            </div>

            <div class="form-group">
                <label for="source" class="col-sm-4 col-form-label">Fuente</label>
                <div>
                    <input type="text" class="form-control" name="source" placeholder="Ingrese la fuente"
                           value="{{old('source')}}">
                </div>
            </div>

            <div class="form-group">
                <label for="url" class="col-sm-4 col-form-label">URL de la noticia</label>
                <div>
                    <input type="text" class="form-control" name="url" placeholder="Ingrese el URL de la noticia"
                           value="{{old('url')}}">
                </div>

            </div>

            <div class="form-group">
                <label for="url_image" class="col-sm-6 col-form-label">Dirección URL de la imagen</label>

                <div>
                    <input type="text" class="form-control" name="url_image"
                           placeholder="Ingrese el URL de la imagen" value="{{old('url_image')}}">
                </div>
            </div>

            <div class="form-group">
                <label for="description" class="col-sm-8 col-form-label">Descripción o reseña de la noticia</label>
                <div>
                    <input type="text" class="form-control" name="description"
                           placeholder="Ingrese descripción breve" value="{{old('description')}}">

                </div>
            </div>

            <div class="form-group ">
                <label for="content" class="col-sm-6 col-form-label">Contenido de la noticia</label>
                <div>
                    <textarea class="form-control" name="content" rows="6" cols="50"
                              placeholder="Ingrese el contenido"
                              value="{{old('content')}}"></textarea>
                </div>
                <small id="inputContent" class="form-text text-muted">Ingrese contenido</small>


            </div>
            <!-- date management -->
            <?php
            date_default_timezone_set('America/Santiago');
            $date_actual = date("d-m-Y");
            ?>
            <div class="col-sm-6">
                <label for="date" class="col-sm-8 col-form-label">Fecha de ingreso</label>
                <div>
                    <input type="text" class="form-control" name="date" type="datetime" value="<?= $date_actual?>">
                </div>
            </div>

            <div>
                <br/>
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
        <div class="text-center p-4" style="background-color: #e3f2fd;"">
            © 2021 Copyright:
            <a class="text-dark" href="https://mdbootstrap.com/">UCN - Desarrollo de Soluciones moviles</a>
        </div>
        <!-- Copyright -->
</footer>
</html>
