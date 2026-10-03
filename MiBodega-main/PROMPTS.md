## Flujo de Prompts para la Construcción de la Aplicación — Mi Bodega (App Cliente)

Prompt 1:

Lo que se le pidió:

Etoy trabajando sobre la pantalla existente screens/inicio/InicioScreen.kt. Necesito incorporar la lógica para buscar productos por nombre. ¿Cómo declaro una variable de estado reactivo var textoBusqueda by remember { mutableStateOf("") } en la parte superior del composable e integro una variable que capture el texto introducido por el usuario sin reiniciar la interfaz en cada recomposición?

Correcciones y ajustes realizados:

Se definió la variable de estado textoBusqueda en InicioScreen.kt utilizando remember { mutableStateOf("") }, permitiendo que Jetpack Compose preserve el texto ingresado durante el ciclo de vida de la pantalla.

Prompt 2:

Lo que se le pidió:

En screens/inicio/InicioScreen.kt, ubica un campo OutlinedTextField en la cabecera del catálogo arriba del LazyRow de categorías. Asigna textoBusqueda como su valor y agrega un ícono de lupa (Icons.Default.Search) en leadingIcon. En el slot trailingIcon, muestra un botón con ícono de equis (Icons.Default.Clear) que únicamente sea visible si textoBusqueda.isNotEmpty() y que al hacerse clic limpie el texto haciendo textoBusqueda = "".

Correcciones y ajustes realizados:

Se maquetó el campo de búsqueda en InicioScreen.kt personalizando las propiedades leadingIcon y trailingIcon, garantizando que el botón de borrado aparezca de forma reactiva cuando el usuario escribe.

Prompt 3:

Lo que se le pidió:

En InicioScreen.kt, necesito que el filtro por texto y el filtro por categoría seleccionada en el LazyRow ("Todos", "Bebidas", "Abarrotes", "Snacks") funcionen de forma simultánea sin anularse. Muestra cómo construir una lista calculada mediante remember(textoBusqueda, categoriaSeleccionada) que evalúe si el nombre del producto contiene el texto en minúsculas y si coincide con la categoría marcada.

Correcciones y ajustes realizados:

Se refactorizó el filtrado sobre la colección de productos en InicioScreen.kt, encadenando las comprobaciones coincideTexto && coincideCategoria para asegurar que ambos filtros trabajen coordinadamente.

Prompt 4:

Lo que se le pidió:

En InicioScreen.kt, cuando la combinación del buscador por texto y la categoría activa no devuelva ningún producto (productosFiltrados.isEmpty()), no quiero que la pantalla se quede en blanco. Oculta la LazyColumn y muestra un contenedor centrado (Column con Alignment.CenterHorizontally) que tenga un ícono ilustrativo y el texto "No se encontraron productos que coincidan con tu búsqueda".

Correcciones y ajustes realizados:

Se integró una estructura de control if (productosFiltrados.isEmpty()) en InicioScreen.kt, sustituyendo la lista de productos por una vista de retroalimentación gráfica y textual para el usuario.

Prompt 5:

Lo que se le pidió:

En InicioScreen.kt, agrega una fila de controles debajo de la barra de búsqueda para permitir al usuario ordenar los productos por precio. Declara un estado var ordenPrecio by remember { mutableStateOf(OrdenPrecio.NINGUNO) } y aplica una transformación sobre la lista previamente filtrada usando .sortedBy { it.precio } para orden ascendente y .sortedByDescending { it.precio } para descendente.

Correcciones y ajustes realizados:

Se añadió el selector de ordenamiento en InicioScreen.kt y se encadenó la ordenación por precio a la colección resultante del filtro de búsqueda y categoría.

Prompt 6:

Lo que se le pidió:

Dentro de las tarjetas de producto que se renderizan en la LazyColumn de InicioScreen.kt, añade un botón de corazón (IconButton) en la esquina superior derecha. Si el producto está marcado como favorito debe mostrar Icons.Filled.Favorite en color rojo; si no, debe mostrar Icons.Outlined.FavoriteBorder. Configura la tarjeta para aceptar un parámetro booleano esFavorito y una lambda onToggleFavorito: () -> Unit.

Correcciones y ajustes realizados:

Se personalizó el diseño de la tarjeta de producto en InicioScreen.kt, desacoplando el componente e introduciendo el botón interactivo que alterna su ícono según el estado recibido.

Prompt 7:

Lo que se le pidió:

Para evitar que los productos marcados como favoritos se borren al cambiar de pantalla, eleva el estado a ClienteApp.kt. Declara var favoritos by remember { mutableStateOf(setOf<Int>()) } y crea la función toggleFavorito(productoId: Int) que agregue el ID si no existe o lo elimine si ya está presente. Pasa este estado y función hacia InicioScreen.kt.

Correcciones y ajustes realizados:

Se implementó el patrón State Hoisting en ClienteApp.kt, centralizando la colección de identificadores favoritos para conservarla durante toda la sesión del usuario.

Prompt 8:

Lo que se le pidió:

En ClienteApp.kt e InicioScreen.kt, agrega una opción para filtrar y visualizar únicamente los productos cuyos IDs pertenezcan al conjunto favoritos (productos.filter { it.id in favoritos }). Si el conjunto está vacío, muestra una vista explicativa con un ícono de corazón y la leyenda "Aún no has agregado productos a tus favoritos".

Correcciones y ajustes realizados:

Se derivó la sublista de productos favoritos en ClienteApp.kt y se añadió la vista alternativa de estado vacío para cuando el usuario no haya guardado ningún artículo.

Prompt 9:

Lo que se le pidió:

En la barra de navegación inferior de ClienteApp.kt, añade la opción "Favoritos" con el ícono Icons.Default.Favorite. Haz que al presionar dicho botón se muestre el catálogo filtrado con los productos guardados por el usuario, permitiendo agregar elementos al carrito directamente desde esta vista.

Correcciones y ajustes realizados:

Se registró el nuevo destino en la barra inferior de ClienteApp.kt, asociando la selección de la pestaña con el despliegue de la lista de elementos favoritos.

Prompt 10:

Lo que se le pidió:

En la pantalla existente screens/login/LoginScreen.kt, necesito implementar la lógica de autenticación del usuario. Al presionar el botón "Iniciar Sesión", verifica que el campo de correo sea igual a cliente@bodega.com y la contraseña a 123456. Si los datos son correctos, ejecuta la lambda onLoginExitoso; de lo contrario, activa un estado de error.

Correcciones y ajustes realizados:

Se programó la evaluación condicional dentro del evento onClick del botón principal en LoginScreen.kt, restringiendo el acceso únicamente a los datos autorizados.

Prompt 11:

Lo que se le pidió:

En screens/login/LoginScreen.kt, crea la variable de estado var errorAutenticacion by remember { mutableStateOf(false) }. Si la validación de credenciales falla, cambia este estado a true para mostrar un contenedor o texto en color rojo (MaterialTheme.colorScheme.error) debajo del formulario con el mensaje "Correo o contraseña incorrectos". Resetea el error cuando el usuario vuelva a escribir en los campos.

Correcciones y ajustes realizados:

Se incorporó el mensaje condicional de alerta en LoginScreen.kt, ofreciendo retroalimentación visual clara ante un intento fallido de inicio de sesión.

Prompt 12:

Lo que se le pidió:

En el archivo existente screens/registro/RegistroScreen.kt, agrega validación al formulario de creación de cuenta (Nombre, Correo, Contraseña, Teléfono). Declara variables de estado para controlar los errores de cada campo. Al hacer clic en "Registrarse", si un campo está vacío, establece su bandera de error en true para que el contorno del OutlinedTextField se pinte de rojo (isError = true).

Correcciones y ajustes realizados:

Se conectó la propiedad isError de cada campo de texto en RegistroScreen.kt con estados de comprobación activados al presionar el botón de registro.

Prompt 13:

Lo que se le pidió:

Complementa las validaciones de screens/registro/RegistroScreen.kt agregando la propiedad supportingText en cada OutlinedTextField de modo que, cuando isError sea true, muestre un mensaje en texto rojo que indique "Este campo es obligatorio". Bloquea la navegación a la pantalla principal mientras exista al menos un campo sin completar.

Correcciones y ajustes realizados:

Se añadieron los textos de asistencia en los inputs de RegistroScreen.kt, impidiendo el avance del usuario hasta que todos los campos requeridos contengan información.

Prompt 14:

Lo que se le pidió:

Ajusta la navegación entre screens/registro/RegistroScreen.kt y la pantalla existente screens/terminos/TerminosScreen.kt. Cuando el usuario haga clic en "Ver términos y condiciones", abre TerminosScreen.kt y asegura que el botón "Aceptar y Volver" regrese al formulario de registro conservando los datos que el usuario ya había escrito.

Correcciones y ajustes realizados:

Se configuraron las lambdas de navegación en ClienteApp.kt para permitir el paso hacia TerminosScreen.kt y retornar al registro preservando los estados de entrada.

Prompt 15:

Lo que se le pidió:

En ClienteApp.kt, define la fuente única de verdad para el carrito de compras mediante var carrito by remember { mutableStateOf(mapOf<Int, Int>()) } (ID de producto a cantidad). Crea las funciones agregarAlCarrito(id), decrementarProducto(id) y eliminarProducto(id) asegurando que modifiquen el estado inmutablemente para actualizar todas las pantallas.

Correcciones y ajustes realizados:

Se estructuró el manejo del carrito global en ClienteApp.kt, proveyendo las lambdas de modificación requeridas por las pantallas de catálogo, detalle y carrito.

Prompt 16:

Lo que se le pidió:

En la barra de herramientas superior (TopAppBar) o en la barra de navegación de ClienteApp.kt, envuelve el ícono del carrito en un componente BadgedBox. En el slot badge, coloca un Badge que muestre el texto con la suma de todas las unidades agregadas (carrito.values.sum()).

Correcciones y ajustes realizados:

Se integró el BadgedBox en ClienteApp.kt, vinculando la cifra del badge al cálculo del total de ítems contenidos en el mapa reactivo del carrito.

Prompt 17:

Lo que se le pidió:

Ajusta la lógica del Badge en ClienteApp.kt para que únicamente se renderice cuando la suma total de productos sea mayor a cero (if (totalProductos > 0)). Si el carrito se vacía por completo, la burbuja roja debe desaparecer automáticamente sin dejar espacios vacíos en la barra.

Correcciones y ajustes realizados:

Se envolvió la llamada al componente Badge en un bloque condicional en ClienteApp.kt, controlando su visibilidad de acuerdo a la cantidad actual de productos.

Prompt 18:

Lo que se le pidió:

En la pantalla existente screens/carrito/CarritoScreen.kt, evalúa si carrito.isEmpty(). Si no hay productos, oculta la lista y el resumen financiero, y dibuja un layout centrado con el ícono de una bolsa de compras, el texto "Tu carrito está vacío" y un botón "Explorar productos" que al presionarse ejecute la navegación de regreso a InicioScreen.kt.

Correcciones y ajustes realizados:

Se maquetó la vista condicional de estado vacío en CarritoScreen.kt, ofreciendo una experiencia fluida al usuario para retornar al catálogo a seleccionar productos.

Prompt 19:

Lo que se le pidió:

En screens/carrito/CarritoScreen.kt, no borres el producto de inmediato cuando el usuario presione el ícono de papelera en una tarjeta. En su lugar, declara el estado var productoAEliminar by remember { mutableStateOf<Producto?>(null) } y asigna el objeto seleccionado al hacer clic en el botón de eliminación.

Correcciones y ajustes realizados:

Se introdujo la variable de estado productoAEliminar en CarritoScreen.kt para capturar el ítem que el usuario desea remover antes de procesar la baja.

Prompt 20:

Lo que se le pidió:

En screens/carrito/CarritoScreen.kt, muestra un AlertDialog cuando productoAEliminar != null. El diálogo debe incluir el título "¿Eliminar producto?", el texto "¿Estás seguro de que deseas quitar [Nombre] del carrito?", el botón "Cancelar" (que resetea la variable a null) y el botón "Eliminar" (que ejecuta onEliminarConfirmado y resetea la variable).

Correcciones y ajustes realizados:

Se maquetó el AlertDialog en CarritoScreen.kt, asegurando que la remoción efectiva del artículo solo se ejecute tras la confirmación explícita del usuario.

Prompt 21:

Lo que se le pidió:

Configura la navegación en ClienteApp.kt para que al hacer clic en cualquier tarjeta de producto de InicioScreen.kt, se navegue a la pantalla existente screens/detalle/DetalleProductoScreen.kt pasando el productoId como parámetro de ruta. En DetalleProductoScreen.kt, busca el producto en DatosFake.kt e integra el botón para agregarlo al carrito.

Correcciones y ajustes realizados:

Se parametrizó la ruta de detalle en ClienteApp.kt y se conectó DetalleProductoScreen.kt para mostrar la información completa del artículo y permitir su compra.

Prompt 22:

Lo que se le pidió:

En el archivo existente screens/entrega/DatosEntregaScreen.kt, aplica validaciones a los campos de formulario: Dirección, Teléfono de contacto y Referencia. Al intentar presionar "Continuar", si falta algún dato obligatorio, resalta los bordes del campo afectado en rojo (isError = true), muestra el texto de advertencia e impide pasar a la pantalla de confirmación.

Correcciones y ajustes realizados:

Se implementó la lógica de validación previa al avance en DatosEntregaScreen.kt, notificando al usuario mediante bordes rojos sobre los campos faltantes.

Prompt 23:

Lo que se le pidió:

En screens/entrega/DatosEntregaScreen.kt, agrega un grupo de selección con componentes RadioButton para la modalidad de entrega: "Envío a Domicilio (S/ 5.00)" y "Recojo en Tienda (S/ 0.00)". Permite que el usuario elija una opción y eleva el valor del costo de envío (5.0f o 0.0f) al estado global de la app.

Correcciones y ajustes realizados:

Se maquetó la sección de selección de entrega con RadioButton en DatosEntregaScreen.kt, conectando la elección del usuario con la variable de costo de envío.

Prompt 24:

Lo que se le pidió:

En la pantalla existente screens/confirmacion/ConfirmacionScreen.kt, recibe la lista de productos seleccionados, sus cantidades y el costo de envío. Muestra el desglose explícito: Subtotal de artículos, Costo de envío ("S/ 5.00" o "S/ 0.00") y el Total General (Subtotal + CostoEnvio), recalculando el monto total automáticamente sin requerir botones adicionales.

Correcciones y ajustes realizados:

Se maquetó el resumen financiero en ConfirmacionScreen.kt, presentando el cálculo reactivo del monto a pagar según los productos en el carrito y el tipo de envío seleccionado.

Prompt 25:

Lo que se le pidió:

En ClienteApp.kt, crea la estructura de datos Pedido (con id, fecha, productos, modalidad, costoEnvio y total) y declara el estado reactivo val historialPedidos = remember { mutableStateListOf<Pedido>() } para almacenar las órdenes confirmadas durante la sesión activa de la aplicación.

Correcciones y ajustes realizados:

Se definió el modelo de datos para las compras en ClienteApp.kt y se inicializó la colección mutable en memoria para guardar las órdenes procesadas.

Prompt 26:

Lo que se le pidió:

En screens/confirmacion/ConfirmacionScreen.kt y ClienteApp.kt, programa la acción del botón "Confirmar Pedido". Debe crear e insertar un nuevo objeto Pedido en historialPedidos, vaciar el carrito haciendo carrito = emptyMap() y navegar a InicioScreen.kt utilizando popUpTo para remover de la pila de navegación las pantallas del proceso de compra.

Correcciones y ajustes realizados:

Se programó el flujo de finalización de compra en ClienteApp.kt, registrando la transacción, vaciando el carrito y restableciendo la pila de pantallas para evitar retornos con el botón físico.

Prompt 27:

Lo que se le pidió:

En ClienteApp.kt, crea una sección o pantalla para mostrar la lista de compras guardadas en historialPedidos. Cada tarjeta debe exhibir el identificador de la orden, la fecha, el tipo de entrega ("Delivery" o "Recojo en Tienda"), el desglose de productos y el total pagado en soles. Si no hay pedidos previos, muestra la alerta de historial vacío.

Correcciones y ajustes realizados:

Se maquetó la vista de historial iterando sobre la colección historialPedidos, ofreciendo un registro ordenado de las transacciones finalizadas por el cliente.

Prompt 28:

Lo que se le pidió:

En la sección de perfil de ClienteApp.kt, coloca un componente Switch etiquetado como "Modo Oscuro". Eleva la variable booleana esModoOscuro hasta MainActivity.kt para que el contenedor principal modifique dinámicamente la paleta de colores del MaterialTheme entre darkColorScheme() y lightColorScheme().

Correcciones y ajustes realizados:

Se conectó la conmutación del tema visual en MainActivity.kt, permitiendo que el estado modificado desde la pantalla de perfil altere el esquema de colores de toda la aplicación.

Prompt 29:

Lo que se le pidió:

En ClienteApp.kt, reemplaza los cambios bruscos entre pantallas personalizando el contenedor de navegación (NavHost). Configura enterTransition y exitTransition usando composiciones de animaciones de desvanecimiento y desplazamiento lateral (fadeIn() + slideInHorizontally()) para brindarle mayor fluidez a la app.

Correcciones y ajustes realizados:

Se definieron las animaciones de transición en la estructura de rutas dentro de ClienteApp.kt, logrando un paso suave y profesional entre las distintas pantallas.