import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewChatUsersSearchComponent } from './new-chat-users-search.component';

describe('NewChatUsersSearchComponent', () => {
  let component: NewChatUsersSearchComponent;
  let fixture: ComponentFixture<NewChatUsersSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewChatUsersSearchComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewChatUsersSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
