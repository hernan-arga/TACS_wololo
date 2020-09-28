import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';

import { TokenStorageService } from '../_services/token-storage.service';

const TOKEN_HEADER_KEY = 'Authorization';
const EXCEPTIONS_URL_NO_INTERCEPT = ["https://cors-anywhere.herokuapp.com", "http://cors-anywhere.herokuapp.com"]

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private token: TokenStorageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let authReq = req;
    const token = this.token.getToken();
    if (token != null && this.isARequestWhoNeedIntercept(req)) {
      authReq = req.clone({ headers: req.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token) });
    }
    return next.handle(authReq);
  }

  isARequestWhoNeedIntercept(req: HttpRequest<any>): boolean{
    return !EXCEPTIONS_URL_NO_INTERCEPT.some(e => req.url.startsWith(e));
  }
}

export const authInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
];