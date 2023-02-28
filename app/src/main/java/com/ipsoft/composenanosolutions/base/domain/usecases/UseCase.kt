package com.ipsoft.composenanosolutions.base.domain.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//No desenvolvimento orientado a objetos, um Use Case também pode ser visto como uma classe que
// representa uma funcionalidade ou processo específico do sistema,
// e que encapsula a lógica de negócios necessária para executar esse processo.
// Essas classes são geralmente implementadas com o padrão de design "Command", e podem ser
// reutilizadas em diferentes partes do sistema para fornecer funcionalidades semelhantes.
abstract class UseCase<out Result, in Params> where Result : Any {

    abstract suspend fun run(params: Params): Result

    operator fun invoke(
        params: Params,
        scope: CoroutineScope,
        onResult: (Result) -> Unit = {},
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

    class None
}