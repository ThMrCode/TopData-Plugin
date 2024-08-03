# TopData-Plugin
Plugin de Minecraft para java 16, version de paper 1.18.2.388

# HELP GUI
  - "/createTopKills(ctk) (String)name (double)height"
  - "/createTopHoras(cth) (String)name (double)height"
  - "/createTopBloques(ctb) (String)name (double)height"
  - "/removeTopData(rtd) (String)type (String)name"
  - "/listTopData(ltd)"
  - "/configTopData(cftd) help"
  - "/configTopData(cftd) reload"
  - "/configTopData(cftd) TopData reload"
  - "/configTopData(cftd) Hologram reload"
  - "/configTopData(cftd) REMOVEARMORSTAND"

# DESCRIPTION
Archivo a modificar config.yml, editar el texto y colores de config.type.format y config.type.default para los types (kills, horas, bloques), todo comando exige permisos OP (Server Operator).

Los comandos configTopData(cftd) ayudan con el manejo de los archivos:
  /cftd reload: Recarga el archivo config.yml, usar tras una edicion del formato en config.yml (config.type.format o config.type.default)
  /cftd TopData reload: Recarga el archivo data.yml, (archivo donde se almacenan los datos de usuarios: kills horas bloques,) usar tras     una modificacion del archivo data.yml
  /cftd Hologram reload: Actualiza los hologramas TopData actuales, usar tras /cftd TopData reload para hacer los cambios visibles

El comando listTopdata(ltd) muestra todos los hologramas TopData existentes

Crear Hologramas con /createTopKills(ctk) /createTopHoras(cth) /createTopBloques(ctb), no olvidar el name y el height (se posicionaran a una altura height por encima de la posicion actual del jugador que llama al comando)

Eliminar Hologramas con /removeTopData(rtd) type name para los types (kills, horas, bloques)
