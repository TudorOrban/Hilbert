import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatMediumCardComponent } from './chat-medium-card.component';

describe('ChatMediumCardComponent', () => {
  let component: ChatMediumCardComponent;
  let fixture: ComponentFixture<ChatMediumCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChatMediumCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatMediumCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
