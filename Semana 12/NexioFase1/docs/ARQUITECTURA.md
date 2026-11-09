# Arquitectura MVVM

```mermaid
flowchart TD
    Pantalla[Compose + collectAsStateWithLifecycle] --> VM[ViewModel + StateFlow de UiState]
    VM --> IR[ItemRepository: interface]
    IR --> Impl[ItemRepositoryImpl]
    Impl --> API[Retrofit: lista y detalle]
    Impl --> DB[Room: Entity / Dao / Database]
    API -->|DTO convertido en Entity| DB
    DB -->|Flow de entidades convertido en Item| VM
    VM --> NI[Notificador: interface]
    NI --> NA[Canal + permiso + NotificationCompat]
    Login[LoginViewModel] --> Auth[AuthRepository: interface]
    Auth --> Firebase[Firebase Authentication + rol en Firestore]
```

## Ubicación de las clases
| Carpeta | Responsabilidad |
|---|---|
| domain/model | Item, Sesion, Rol y formato/conversión de moneda. |
| data/remote | ApiService, RetrofitClient y DTO. |
| data/local | ItemEntity, ItemDao y AppDatabase. |
| data/mapper | Conversión de datos y descripciones en español disponibles offline. |
| data/repository | Contrato ItemRepository e implementación que coordina Retrofit y Room. |
| data/auth | Contrato de acceso y lectura de sesión/rol mediante Firebase. |
| notification | Contrato y adaptación de notificaciones Android. |
| ui | Pantallas, ViewModels, estados, navegación y tema. |
| AppContainer | Construye dependencias y Factory de ViewModels. |
| NexioApplication | Conserva un AppContainer durante la vida del proceso. |

El ViewModel no recibe Context. Los composables no consultan directamente Retrofit, Room ni Firebase. La petición del permiso de Android vive en la navegación; MainActivity pasa la respuesta a NotificadorAndroid. Los ViewModel solo conocen la interface Notificador, que tiene un único método: mostrar(titulo, texto).

## Endpoints
- Lista: GET https://dummyjson.com/products/category/smartphones
- Detalle: GET https://dummyjson.com/products/{id}

Firebase se usa para acceso y roles, no para alojar el catálogo. Las cuentas se crean en consola; esta fase no incluye registro de clientes, compras, carrito ni pagos.
