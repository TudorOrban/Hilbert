import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewChatUsersSearchPanelComponent } from './new-chat-users-search-panel.component';

describe('NewChatUsersSearchPanelComponent', () => {
  let component: NewChatUsersSearchPanelComponent;
  let fixture: ComponentFixture<NewChatUsersSearchPanelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewChatUsersSearchPanelComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewChatUsersSearchPanelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
