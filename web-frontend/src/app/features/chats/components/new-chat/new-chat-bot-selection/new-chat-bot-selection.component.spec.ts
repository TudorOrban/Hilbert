import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewChatBotSelectionComponent } from './new-chat-bot-selection.component';

describe('NewChatBotSelectionComponent', () => {
  let component: NewChatBotSelectionComponent;
  let fixture: ComponentFixture<NewChatBotSelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewChatBotSelectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewChatBotSelectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
