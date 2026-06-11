import { KeycloakService } from 'keycloak-angular';
import { Component, OnInit } from '@angular/core';
import { SecurityService } from './services/security.service';
import { KeycloakProfile } from 'keycloak-js';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title = 'costumer-front-angular-app';

  public profile?: KeycloakProfile;

  constructor(
    //public secService: SecurityService,
    public keycloakService: KeycloakService
  ) {}

  ngOnInit(): void {
    if (this.keycloakService.isLoggedIn()) {
      this.keycloakService.loadUserProfile().then((profile) => {
        this.profile = profile;
      });
    }
  }

  async login() {
    await this.keycloakService.login({
      redirectUri: window.location.origin,
    });
  }
  logout() {
    this.keycloakService.logout(window.location.origin);
  }
}
