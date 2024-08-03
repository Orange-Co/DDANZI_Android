package co.orange.data.dataSource

import co.orange.data.dto.IamportBaseResponse
import co.orange.data.dto.request.IamportTokenRequestDto
import co.orange.data.dto.response.IamportTokenDto

interface IamportDataSource {
    suspend fun postToGetIamportToken(request: IamportTokenRequestDto): IamportBaseResponse<IamportTokenDto>
}
