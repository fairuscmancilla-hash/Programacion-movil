package com.cerron.campusgps.data

import com.cerron.campusgps.model.Lugar
interface LugaresRepository { fun obtenerLugares(): List<Lugar> }
