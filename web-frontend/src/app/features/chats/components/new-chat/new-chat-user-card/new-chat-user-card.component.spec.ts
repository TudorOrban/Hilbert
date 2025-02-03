import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewChatUserCardComponent } from './new-chat-user-card.component';

describe('NewChatUserCardComponent', () => {
  let component: NewChatUserCardComponent;
  let fixture: ComponentFixture<NewChatUserCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewChatUserCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewChatUserCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
