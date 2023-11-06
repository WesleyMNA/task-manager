interface IUserJwt {
    id: number,
    name: string,
    email: string,
}

export interface IJwtData {
    iss: string
    sub: string,
    exp: number,
    iat: number,
    context: IUserJwt
}