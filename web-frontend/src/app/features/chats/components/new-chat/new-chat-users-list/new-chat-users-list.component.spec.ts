import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewChatUsersListComponent } from './new-chat-users-list.component';

describe('NewChatUsersListComponent', () => {
  let component: NewChatUsersListComponent;
  let fixture: ComponentFixture<NewChatUsersListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewChatUsersListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewChatUsersListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
