import { ApplicationConfig, provideZoneChangeDetection } from "@angular/core";
import { provideRouter } from "@angular/router";

import { routes } from "./app.routes";
import {
    provideHttpClient,
    withInterceptorsFromDi,
} from "@angular/common/http";
import { RxStompService } from "./features/chats/services/rx-stomp.service";
import { rxStompServiceFactory } from "./features/chats/services/rx-stomp-service-factory";

export const appConfig: ApplicationConfig = {
    providers: [
        provideZoneChangeDetection({ eventCoalescing: true }),
        provideRouter(routes),
        provideHttpClient(withInterceptorsFromDi()),
        {
            provide: RxStompService,
            useFactory: rxStompServiceFactory,
        }
    ],
};
