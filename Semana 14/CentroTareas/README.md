# CentroTareas

## Observación del Paso 1

Al pulsar **Tarea larga (MAL)** la pantalla se queda congelada durante 4 segundos. El botón **Tócame** no suma mientras dura la espera, y el toque recién se procesa cuando el hilo principal se libera.

El texto **Trabajando...** no llega a verse porque `Thread.sleep(4000)` bloquea el hilo principal, que es el mismo que dibuja la pantalla. El mensaje cambia a `Trabajando...`, pero Compose no puede redibujar hasta que termine la espera, y para entonces el mensaje ya cambió a `Terminó`. En la captura tomada durante el bloqueo todavía se lee **Listo**.

Si la espera fuera de 10 segundos, creo que Android mostraría el diálogo **ANR (Application Not Responding)**, que avisa que la app no responde y ofrece cerrarla o esperar. Ese aviso aparece cuando la app no atiende un toque durante unos 5 segundos.
