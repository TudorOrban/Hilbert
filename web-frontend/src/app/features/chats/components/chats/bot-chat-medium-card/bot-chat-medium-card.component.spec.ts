import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotChatMediumCardComponent } from './bot-chat-medium-card.component';

describe('BotChatMediumCardComponent', () => {
  let component: BotChatMediumCardComponent;
  let fixture: ComponentFixture<BotChatMediumCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotChatMediumCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotChatMediumCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
