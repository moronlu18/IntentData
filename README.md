# Intent Implícitos

## Descripción 
En este ejercicio se muestra como enviar intent implícitos de forma que
se inicia el componente de otra aplicación que realizará la acción que
se solicita en el intent. Se muestran los siguientes ejemplos:

1. Abrir el navegador con una dirección web predefinida.
2. Realizar una llamada a un número de teléfono.
3. Mostrar un número de teléfono en el dial.
4. Realizar una búsqueda de una ubicación en un mapa
5. Mostrar la lista de contactos
6. Editar el primer contacto de la agenda
## Funcionalidades Implementadas

A continuación, se detallan los tipos de `Intents` implícitos utilizados en el proyecto junto con ejemplos prácticos:

1. **Abrir el navegador** con una URL específica.
   ```kotlin
   intent = Intent(Intent.ACTION_VIEW)
   intent.setData(Uri.parse("http://www.moronlu18.com/wordpress"))
   startActivity(intent)
   ```

2. **Realizar una llamada directa** a un número de teléfono, verificando los permisos necesarios.
   ```kotlin
   if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
       intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)123456789"))
       startActivity(intent)
   } else {
       requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), PERMISSIONS_REQUEST_CALL_PHONE)
   }
   ```

3. **Mostrar un número en el dial**, sin realizar la llamada.
   ```kotlin
   intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+34)608033422"))
   startActivity(intent)
   ```

4. **Buscar una ubicación en Google Maps**.
   ```kotlin
   intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=PC Box Arango"))
   startActivity(intent)
   ```

5. **Acceder y visualizar la lista de contactos**.
   ```kotlin
   intent = Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"))
   startActivity(intent)
   ```

6. **Editar el primer contacto** de la lista de contactos.
   ```kotlin
   intent = Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/1"))
   startActivity(intent)
   ```

## Gestión de Permisos

El manejo de permisos es fundamental para asegurar que la aplicación opere correctamente y respete la privacidad del usuario:

- **Verificación de permisos**: Se utiliza `checkSelfPermission` para verificar si la aplicación posee un permiso específico.
- **Solicitud de permisos**: `requestPermissions` se emplea para solicitar permisos que no han sido otorgados.
- **Racionalización de permisos**: Se utiliza `shouldShowRequestPermissionRationale` para explicar al usuario por qué se necesitan ciertos permisos si inicialmente los rechaza.

## Consideraciones de API

El manejo de permisos detallado es requerido a partir de Android 6.0 (API nivel 23). Las aplicaciones deben asegurarse de solicitar permisos en tiempo de ejecución para acceder a funcionalidades que implican privacidad o seguridad del usuario.

## Conclusión

Este proyecto proporciona un marco sólido para entender cómo interactuar con componentes y servicios externos en Android a través de `Intents` implícitos. Es esencial manejar adecuadamente los permisos para proteger la seguridad y privacidad del usuario mientras se provee una experiencia fluida y funcional.